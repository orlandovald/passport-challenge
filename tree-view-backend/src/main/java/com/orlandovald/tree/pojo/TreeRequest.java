package com.orlandovald.tree.pojo;

/**
 * Represents a request message
 */
public class TreeRequest {

    private RequestType type;
    private Node node;
    private int count;

    public RequestType getType() {
        return type;
    }

    public void setType(RequestType type) {
        this.type = type;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "TreeCommand{" +
                "type=" + type +
                ", node=" + node +
                ", childCount=" + count +
                '}';
    }
}
