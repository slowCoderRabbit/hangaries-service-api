package com.hangaries.service.sse;

import com.hangaries.model.ConfigMaster;
import com.hangaries.model.User;
import com.hangaries.service.config.impl.ConfigServiceImpl;
import com.hangaries.service.login.impl.LoginServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.LocalTime;
import java.util.*;

@Service
public class SSEServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(SSEServiceImpl.class);
    private static LocalTime pushNotificationStartTime;
    private static LocalTime pushNotificationEndTime;
    Map<String, List<SseEmitter>> emitterMap = new HashMap<>();
    @Autowired
    LoginServiceImpl userService;
    @Autowired
    ConfigServiceImpl configService;

    @Bean(initMethod = "init")
    private void loadPushNotificationTime() {
        logger.info("Loading Push Notification Start and End Time");
        List<ConfigMaster> configMasterListStart = configService.getConfigDetailsByCriteria("R001", "ALL", "PUSH_NOTIFICATION_START_TIME");
        List<ConfigMaster> configMasterListEnd = configService.getConfigDetailsByCriteria("R001", "ALL", "PUSH_NOTIFICATION_END_TIME");

        String startTime = configMasterListStart.stream().findFirst().get().getConfigCriteriaValue();
        String endTime = configMasterListEnd.stream().findFirst().get().getConfigCriteriaValue();

        logger.info("Push Notification start time = [{}] and end time = [{}].", startTime, endTime);

        pushNotificationStartTime = convertStringToTime(startTime);
        pushNotificationEndTime = convertStringToTime(endTime);

        logger.info("Successfully converted to local start time = [{}] and end time = [{}] loaded.", pushNotificationStartTime, pushNotificationEndTime);

    }

    private LocalTime convertStringToTime(String timeString) {
        return LocalTime.parse(timeString);
    }

    public SseEmitter getSseEmitter(String userLoginId) {
        logger.info("Subscribe request received for userLoginId=[{}]", userLoginId);

        String key = getEmitterKey(userLoginId);
        if (StringUtils.isBlank(key)) {
            return null;
        }

        SseEmitter emitter = getEmitter(key);
        emitter.onCompletion(() -> emitterMap.get(key).remove(emitter));
        emitter.onTimeout(() -> emitterMap.get(key).remove(emitter));
        emitter.onError((e) -> emitterMap.get(key).remove(emitter));
        return emitter;
    }

    private String getEmitterKey(String userLoginId) {
        String key = null;
        User user = userService.getEmployeeByLoginId(userLoginId);
        if (null != user) {
            key = user.getRestaurantId() + "" + user.getStoreId();
        }
        logger.info("Generated emitter key = [{}] for userLoginId=[{}]", key, userLoginId);
        return key;
    }

    private SseEmitter getEmitter(String key) {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        logger.info("Emitter created [{}] for key=[{}]", emitter, key);
        sendInitEvent(emitter);
        List<SseEmitter> emitterList = emitterMap.get(key);
        if (null == emitterList || emitterList.isEmpty()) {
            emitterList = new ArrayList<>();
        }
        emitterList.add(emitter);
        emitterMap.put(key, emitterList);
        return emitter;
    }

    private void sendInitEvent(SseEmitter emitter) {
        try {
            emitter.send(SseEmitter.event().name("INIT"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void dispatchEvents(String key, String text) {
        logger.info("DispatchEvent request received for eventKey=[{}]", key);
        LocalTime timeNow = LocalTime.now();
        if (timeNow.isAfter(pushNotificationStartTime) && timeNow.isBefore(pushNotificationEndTime)) {
            List<SseEmitter> offLineEmitters = new ArrayList<>();
            List<SseEmitter> emitters = emitterMap.get(key);
            for (SseEmitter emitter : emitters) {
                try {
                    logger.info("Dispatching request received for key=[{}] for emitter = [{}]", key, emitter);
                    emitter.send(SseEmitter.event().id(String.valueOf((new Random().nextInt() & Integer.MAX_VALUE))).name("message\n").data(text + "\n\n"));
                } catch (Exception e) {
                    offLineEmitters.add(emitter);
                }
            }
            emitters.removeAll(offLineEmitters);
        } else {
            logger.info("Current time = [{}]. Notification not dispatched outside start time = [{}] and end time = [{}] ", timeNow, pushNotificationStartTime, pushNotificationEndTime);
        }

    }

}
