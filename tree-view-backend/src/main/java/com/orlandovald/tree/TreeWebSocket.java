package com.orlandovald.tree;

import com.orlandovald.tree.pojo.*;
import io.micronaut.websocket.WebSocketBroadcaster;
import io.micronaut.websocket.WebSocketSession;
import io.micronaut.websocket.annotation.*;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Predicate;

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
    public Publisher<TreeResponse> onMessage(TreeRequest message, WebSocketSession session) {
        log.info("Message received from session: " + session.getId() + " Message: " + message);
        try {
            TreeResponse resp = service.process(message);
            return broadcaster.broadcast(resp);
        } catch(TreeException ex) {
            TreeResponse resp = new TreeResponse(ResponseType.ERROR);
            resp.setMsg(ex.getMessage());
            resp.setNode(message.getNode());
            return broadcaster.broadcast(resp, isSender(session.getId()));
        }
    }

    @OnClose
    public Publisher<String> onClose(WebSocketSession session) {
        String msg = "Adios!";
        log.info("Closing session: " + session.getId());
        return broadcaster.broadcast(msg);
    }

    @OnError
    public Publisher<TreeResponse> onError(WebSocketSession session, Exception ex) {
        log.error("Oops! Something went really wrong!", ex);
        TreeResponse resp = new TreeResponse(ResponseType.ERROR);
        resp.setMsg("Oops! Something went really wrong!");
        return broadcaster.broadcast(resp, isSender(session.getId()));
    }

    /**
     * Checks if the provided session Id correspond to the corresponding recipient.
     * Use to filter broadcasting to only the originator.
     * @param sessionId
     * @return
     */
    private Predicate<WebSocketSession> isSender(String sessionId) {
        return s -> s.getId().equals(sessionId);
    }

}
