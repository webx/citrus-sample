package com.alibaba.sample.petstore.dal.dataobject;

public class Sequence {
    private String name;
    private int nextId;

    public Sequence() {
    }

    public Sequence(String name, int nextId) {
        this.name = name;
        this.nextId = nextId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNextId() {
        return nextId;
    }

    public void setNextId(int nextId) {
        this.nextId = nextId;
    }
}
