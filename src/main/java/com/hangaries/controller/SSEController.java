package com.hangaries.controller;

import com.hangaries.service.sse.SSEServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class SSEController {

    private static final Logger logger = LoggerFactory.getLogger(SSEController.class);
    @Autowired
    SSEServiceImpl sseService;

    @GetMapping(value = "subscribe", consumes = MediaType.ALL_VALUE)
    public SseEmitter subscribe(@RequestParam String userLoginId) {
        return sseService.getSseEmitter(userLoginId);
    }

    @PostMapping(value = "dispatchEvents")
    public void dispatchEvents(@RequestParam String eventKey, @RequestParam String text) {
        sseService.dispatchEvents(eventKey, text);
    }

}
