package com._1irda.concurrency;

public class Philosopher extends Thread {

    private static final int LEFT = 0;

    private static final int RIGHT = 1;

    private static final int FOOD_DURATION = 2000;

    private final String name;

    private final Stick[] sticks;

    private State currentState;

    private int foodDuration;

    public enum State {

        THINK(10),

        HUNGRY(0),

        EAT(5);

        private final int duration;

        State(int duration) {
            this.duration = duration;
        }

        private State next() {
            return values()[(ordinal() + 1) % values().length];
        }

        public State waitAndNext() {
            if (duration > 0) {
                try {
                    Thread.sleep(duration);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return next();
        }

        public int getDuration() {
            return this.duration;
        }
    }

    public Philosopher(String name, Stick left, Stick right) {
        this.name = name;
        sticks = new Stick[]{left, right};
        currentState = State.THINK;
    }

    private void think() {
        System.out.println(name + " think");
    }

    private void hungry() {
        while (!isOwner()) {
            if (sticks[LEFT].isFree()) {
                sticks[LEFT].take(this);
            } else if (sticks[RIGHT].isFree()) {
                sticks[RIGHT].take(this);
            }
        }
    }

    private void eat() {
        System.out.println(name + " eat");
        if (!isOwner()) {
            throw new IllegalStateException("Philosopher needs sticks to eat");
        }
        foodDuration += currentState.getDuration();
        putSticks();
    }

    private void putSticks() {
        sticks[LEFT].put();
        sticks[RIGHT].put();
    }

    private boolean isOwner() {
        return sticks[LEFT].getOwner() == this && sticks[RIGHT].getOwner() == this;
    }

    @Override
    public void run() {
        do {
            switch (currentState) {
                case THINK -> think();
                case HUNGRY -> hungry();
                case EAT -> eat();
            }
            currentState = currentState.waitAndNext();
        } while (foodDuration < FOOD_DURATION);

        System.out.println(name + " SATIATED !!!");
    }
}