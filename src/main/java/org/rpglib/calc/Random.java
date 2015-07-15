package org.rpglib.calc;

import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;

public class Random extends java.util.Random {
    public static int nextInt(int min, int max) {
        java.util.Random random = new java.util.Random();
        random.setSeed(System.nanoTime());
        return random.nextInt((max - min) + 1) + min;
    }

    public boolean nextBoolean() {
        super.setSeed(System.nanoTime());
        return super.nextBoolean();
    }

    public int nextPercent() {
        return nextInt(1, 100);
    }

    public Outcome chooseOutcome(List<? extends Outcome> outcomes) {
        super.setSeed(System.nanoTime());
        Collections.shuffle(outcomes, this);
        Outcome chosenOutcome = null;

        do {
            for (Outcome outcome : outcomes) {
                if (nextPercent() <= outcome.getPercentChance()) {
                    return outcome;
                }
            }
        } while (chosenOutcome == null);

        // this should never happen.
        return null;
    }
}
