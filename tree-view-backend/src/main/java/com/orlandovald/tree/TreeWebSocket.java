package com.orlandovald.tree;

import io.micronaut.websocket.WebSocketBroadcaster;
import io.micronaut.websocket.WebSocketSession;
import io.micronaut.websocket.annotation.OnClose;
import io.micronaut.websocket.annotation.OnMessage;
import io.micronaut.websocket.annotation.OnOpen;
import io.micronaut.websocket.annotation.ServerWebSocket;
import org.reactivestreams.Publisher;

/**
 * WebSocket handler
 */
@ServerWebSocket("/ws/tree")
public class TreeWebSocket {

    private final WebSocketBroadcaster broadcaster;

    public TreeWebSocket(WebSocketBroadcaster broadcaster) {
        this.broadcaster = broadcaster;
    }

    @OnOpen
    public Publisher<String> onOpen(WebSocketSession session) {
        return broadcaster.broadcast("Hola!");
    }

    @OnMessage
    public Publisher<String> onMessage(String message, WebSocketSession session) {
        String msg = "Message: " + message;
        return broadcaster.broadcast(msg);
    }

    @OnClose
    public Publisher<String> onClose(WebSocketSession session) {
        String msg = "Adios!";
        System.out.println(msg);
        return broadcaster.broadcast(msg);
    }

}
