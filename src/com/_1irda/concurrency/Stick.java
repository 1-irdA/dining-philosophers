package com._1irda.concurrency;

public class Stick {

    private Philosopher owner;

    public Stick() { }

    public synchronized void take(Philosopher newOwner) {
        if (isFree()) {
            this.owner = newOwner;
        }
    }

    public synchronized void put() {
        this.owner = null;
    }

    public synchronized boolean isFree() {
        return this.owner == null;
    }

    public Philosopher getOwner() {
        return owner;
    }
}
