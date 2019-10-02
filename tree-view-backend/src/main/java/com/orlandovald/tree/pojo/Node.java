package com.orlandovald.tree.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

/**
 * Represents a Node object
 */
public class Node {

    private int id;
    private String name;
    @JsonProperty("lower_bound")
    private int lowerBound;
    @JsonProperty("upper_bound")
    private int upperBound;
    private Integer[] childs;
    private LocalDate created;

    public Node() {
    }

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

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLowerBound() {
        return lowerBound;
    }

    public void setLowerBound(int lowerBound) {
        this.lowerBound = lowerBound;
    }

    public int getUpperBound() {
        return upperBound;
    }

    public void setUpperBound(int upperBound) {
        this.upperBound = upperBound;
    }

    public Integer[] getChilds() {
        return childs;
    }

    public void setChilds(Integer[] childs) {
        this.childs = childs;
    }

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
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
