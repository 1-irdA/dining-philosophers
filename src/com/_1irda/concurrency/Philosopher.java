package com._1irda.concurrency;

public class Philosopher extends Thread {

    private static final int LEFT = 0;

    private static final int RIGHT = 1;

    private static final int FOOD_DURATION = 1000;

    private final String name;

    private boolean isRightHanded;

    private final Stick[] sticks;

    private State currentState;

    private int foodDuration;

    public enum State {

        THINK(2),

        HUNGRY(0),

        EAT(1);

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

    public Philosopher(String name, boolean rightHanded, Stick left, Stick right) {
        this.name = name;
        isRightHanded = rightHanded;
        sticks = new Stick[]{left, right};
        currentState = State.THINK;
    }

    private void think() {
        System.out.println(name + " think");
    }

    private void hungry() {
        Stick s1 = isRightHanded ? sticks[RIGHT] : sticks[LEFT];
        Stick s2 = isRightHanded ? sticks[LEFT] : sticks[RIGHT];
        while (!isOwner()) {
            if (s1.isFree()) {
                s1.take(this);
                if (s2.isFree()) {
                    s2.take(this);
                }
            } else if (s2.isFree()) {
                s2.take(this);
                if (s1.isFree()) {
                    s1.take(this);
                }
            }
            if (!isOwner()) {
                if (s1.getOwner() == this) {
                    s1.put(this);
                } else if (s2.getOwner() == this) {
                    s2.put(this);
                }
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
        sticks[LEFT].put(this);
        sticks[RIGHT].put(this);
    }

    private boolean isOwner() {
        return sticks[LEFT].getOwner() == this && sticks[RIGHT].getOwner() == this;
    }

    public void setRightHanded(boolean rightHanded) {
        isRightHanded = rightHanded;
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