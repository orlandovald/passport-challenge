package com.orlandovald.tree.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a response message
 */
public class TreeResponse {

    private final ResponseType type;
    private String msg;
    private List<Node> nodes;

    public TreeResponse(ResponseType type) {
        this.type = type;
        this.nodes = new ArrayList<>();
    }

    public ResponseType getType() {
        return type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    @Override
    public String toString() {
        return "TreeResponse{" +
                "type=" + type +
                ", msg='" + msg + '\'' +
                ", nodes=" + nodes +
                '}';
    }
}
