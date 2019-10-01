package com.orlandovald.tree;

import java.time.LocalDate;

/**
 * Represents a Node object
 */
public class Node {

    private final int id;
    private final String name;
    private final int lowerBound;
    private final int upperBound;
    private final Integer[] childs;
    private final LocalDate created;

    public Node(int id, String name, int lowerBound, int upperBound, Integer[] childs, LocalDate created) {
        this.id = id;
        this.name = name;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.childs = childs;
        this.created = created;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getLowerBound() {
        return lowerBound;
    }

    public int getUpperBound() {
        return upperBound;
    }

    public Integer[] getChilds() {
        return childs;
    }

    public LocalDate getCreated() {
        return created;
    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lowerBound=" + lowerBound +
                ", upperBound=" + upperBound +
                ", childs=" + childs +
                ", created=" + created +
                '}';
    }
}
