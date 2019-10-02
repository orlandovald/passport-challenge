package com.orlandovald.tree.pojo;

/**
 * Represents a response message
 */
public class TreeResponse {

    private final ResponseType type;
    private String msg;
    private Node node;

    public TreeResponse(ResponseType type) {
        this.type = type;
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

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    @Override
    public String toString() {
        return "TreeResponse{" +
                "type=" + type +
                ", msg='" + msg + '\'' +
                ", node=" + node +
                '}';
    }
}
