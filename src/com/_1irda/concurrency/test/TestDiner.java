package com._1irda.concurrency.test;

import com._1irda.concurrency.Diner;

public class TestDiner {

    public static void main(String[] args) {
        Diner diner = new Diner(5);
        diner.launch();
    }
}
