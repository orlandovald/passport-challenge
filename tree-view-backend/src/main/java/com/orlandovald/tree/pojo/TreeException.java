package com.orlandovald.tree.pojo;

public class TreeException extends RuntimeException {

    private TreeRequest request;

    public TreeException(String message) {
        super(message);
    }

    public TreeException(String message, Throwable cause) {
        super(message, cause);
    }

    public TreeException(String message, Node request) {
        super(message);
    }

    public TreeException(String message, Throwable cause, Node request) {
        super(message, cause);
    }

    public TreeRequest getRequest() {
        return request;
    }

}
