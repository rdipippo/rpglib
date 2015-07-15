package org.rpglib.persistence;

public class Attack {
    private int minDamage;

    private int maxDamage;

    public Attack() {

    }

    public Attack(int min, int max) {
        this.minDamage = min;
        this.maxDamage = max;
    }

    public int getMinDamage() {
        return minDamage;
    }

    public void setMinDamage(int minDamage) {
        this.minDamage = minDamage;
    }

    public int getMaxDamage() {
        return maxDamage;
    }

    public void setMaxDamage(int maxDamage) {
        this.maxDamage = maxDamage;
    }
}
