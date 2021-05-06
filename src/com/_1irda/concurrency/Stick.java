package com._1irda.concurrency;

import java.util.concurrent.Semaphore;

public class Stick {

    private Philosopher owner;

    public synchronized void take(Philosopher newOwner) {
        if (isFree()) {
            owner = newOwner;
        }
    }

    public synchronized void put() {
        owner = null;
    }

    public synchronized boolean isFree() {
        return owner == null;
    }

    public Philosopher getOwner() {
        return owner;
    }
}
