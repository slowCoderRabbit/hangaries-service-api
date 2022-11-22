package com.hangaries.service.sse;

import com.hangaries.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SSEServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(SSEServiceImpl.class);
    Map<String, List<SseEmitter>> emitterMap = new HashMap<>();

    public SseEmitter getSseEmitter(String storeId) {
        logger.info("Subscribe request received for storeId=[{}]", storeId);
        SseEmitter emitter = getEmitter(storeId);
        emitter.onCompletion(() -> emitterMap.get(storeId).remove(emitter));
        emitter.onTimeout(() -> emitterMap.get(storeId).remove(emitter));
        emitter.onError((e) -> emitterMap.get(storeId).remove(emitter));
        return emitter;
    }

    private SseEmitter getEmitter(String storeId) {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        logger.info("Emitter created [{}] for storeId=[{}]", emitter, storeId);
        sendInitEvent(emitter);
        List<SseEmitter> emitterList = emitterMap.get(storeId);
        if (null == emitterList || emitterList.isEmpty()) {
            emitterList = new ArrayList<>();
        }
        emitterList.add(emitter);
        emitterMap.put(storeId, emitterList);
        return emitter;
    }

    private void sendInitEvent(SseEmitter emitter) {
        try {
            emitter.send(SseEmitter.event().name("INIT"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void dispatchEvents(String storeId, String text) {
        logger.info("DispatchEvent request received for storeId=[{}]", storeId);
        List<SseEmitter> offLineEmitters = new ArrayList<>();
        List<SseEmitter> emitters = emitterMap.get(storeId);
        for (SseEmitter emitter : emitters) {
            try {
                logger.info("Dispatching request received for storeId=[{}] for emitter = [{}]", storeId, emitter);
                emitter.send(SseEmitter.event().name("latestText").data(text));
            } catch (Exception e) {
                offLineEmitters.add(emitter);
            }
        }
        emitters.removeAll(offLineEmitters);

    }

    public void dispatchOrderEvents(Order order) {
        logger.info("DispatchEvent request received for order=[{}]", order);
        List<SseEmitter> offLineEmitters = new ArrayList<>();
        List<SseEmitter> emitters = emitterMap.get(order.getStoreId());
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(SseEmitter.event().name("latestOrders").data(order));
            } catch (Exception e) {
                offLineEmitters.add(emitter);
            }
        }
        emitters.removeAll(offLineEmitters);

    }

}
