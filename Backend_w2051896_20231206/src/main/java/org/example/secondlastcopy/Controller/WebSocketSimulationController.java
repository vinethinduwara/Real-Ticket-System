package org.example.secondlastcopy.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketSimulationController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void broadcastSimulationUpdate(String message) {
        System.out.println("Broadcasting Update: " + message);
        messagingTemplate.convertAndSend("/topic/simulation", message);
    }
}

