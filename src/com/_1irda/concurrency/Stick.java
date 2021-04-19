package com._1irda.concurrency;

import java.util.concurrent.Semaphore;

public class Stick {

    private Philosopher owner;

    private Semaphore semaphore;

    public Stick() {
        semaphore = new Semaphore(1);
    }

    public synchronized void take(Philosopher newOwner) {
        if (isFree()) {
            owner = newOwner;
            semaphore.tryAcquire();
        }
    }

    public synchronized void put() {
        owner = null;
        semaphore.release();
    }

    public synchronized boolean isFree() {
        return owner == null;
    }

    public Philosopher getOwner() {
        return owner;
    }
}
