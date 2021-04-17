package com._1irda.concurrency;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class Diner {

    private final ArrayList<Philosopher> philosophers;

    private final ArrayList<Stick> sticks;

    public Diner(int nbPhilosophers) {
        philosophers = new ArrayList<>();
        sticks = new ArrayList<>();

        IntStream.range(0, nbPhilosophers).forEach(i -> sticks.add(new Stick()));

        IntStream.range(0, nbPhilosophers).forEach(i ->
                philosophers.add(new Philosopher("Philosopher " + i,
                                sticks.get(i),
                                sticks.get((i + 1) % nbPhilosophers))));
    }

    public void launch() {
        philosophers.forEach(Thread::start);
        while (philosophers.size() > 0) {
            for (int i = 0; i < philosophers.size(); i++) {
                if (!philosophers.get(i).isAlive()) {
                    philosophers.remove(i);
                }
            }
        }

        System.out.println("Diner terminated");
    }
}
