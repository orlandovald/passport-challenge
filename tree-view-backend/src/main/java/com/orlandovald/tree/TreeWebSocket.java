package com.orlandovald.tree;

import io.micronaut.websocket.WebSocketBroadcaster;
import io.micronaut.websocket.WebSocketSession;
import io.micronaut.websocket.annotation.OnClose;
import io.micronaut.websocket.annotation.OnMessage;
import io.micronaut.websocket.annotation.OnOpen;
import io.micronaut.websocket.annotation.ServerWebSocket;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * WebSocket handler
 */
@ServerWebSocket("/ws/tree")
public class TreeWebSocket {

    private static final Logger log = LoggerFactory.getLogger(TreeWebSocket.class);

    private final WebSocketBroadcaster broadcaster;
    private final TreeService service;

    public TreeWebSocket(WebSocketBroadcaster broadcaster, TreeService service) {
        this.broadcaster = broadcaster;
        this.service = service;
    }

    @OnOpen
    public Publisher<List<Node>> onOpen(WebSocketSession session) {
        log.info("Opening session: " + session.getId());
        return broadcaster.broadcast(service.getFullTree());
    }

    @OnMessage
    public Publisher<String> onMessage(String message, WebSocketSession session) {
        log.info("Message received from session: " + session.getId() + " Message: " + message);
        String msg = "Message: " + message;
        return broadcaster.broadcast(msg);
    }

    @OnClose
    public Publisher<String> onClose(WebSocketSession session) {
        String msg = "Adios!";
        log.info("Closing session: " + session.getId());
        return broadcaster.broadcast(msg);
    }

}
