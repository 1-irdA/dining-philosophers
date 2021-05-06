package com._1irda.concurrency;

import java.util.concurrent.Semaphore;

public class Stick {

    private Philosopher owner;

    public synchronized void take(Philosopher newOwner) {
        if (isFree()) {
            owner = newOwner;
        }
    }

    public synchronized void put(Philosopher currentOwner) {
        if (getOwner() != currentOwner) {
            throw new IllegalStateException("Current owner is not the stick owner");
        }
        owner = null;
    }

    public synchronized boolean isFree() {
        return owner == null;
    }

    public Philosopher getOwner() {
        return owner;
    }
}
