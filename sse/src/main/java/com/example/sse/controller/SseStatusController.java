package com.example.sse.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/v1/status")
public class SseStatusController {
    private final Map<String, SseEmitter> sseEmitterMap = new ConcurrentHashMap<>();

    @GetMapping("/subscribe/{eventId}")
    public SseEmitter getSseEmitter(@PathVariable String eventId) {
        SseEmitter emitter = new SseEmitter(0L);
        sseEmitterMap.put(eventId, emitter);
        emitter.onCompletion(() -> sseEmitterMap.remove(eventId));
        emitter.onTimeout(() -> sseEmitterMap.remove(eventId));
        return emitter;
    }

    @GetMapping("/start/{eventId}")
    public String start(@PathVariable String eventId) {
        new Thread(() -> {
            try {
                processEvent(eventId);
            } catch (InterruptedException | IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
        return "Stream started";
    }

    private void processEvent(String eventId) throws InterruptedException, IOException {
        sendUpdate(eventId, "PENDING");
        Thread.sleep(3000);

        sendUpdate(eventId, "UPLOADING");
        Thread.sleep(3000);

        sendUpdate(eventId, "UPLOADED");
    }

    private void sendUpdate(String eventId, String status) throws IOException {
        SseEmitter emitter = sseEmitterMap.get(eventId);
        if (emitter == null) {
            return ;
        }

        emitter.send(SseEmitter.event().data(status));
    }
}
