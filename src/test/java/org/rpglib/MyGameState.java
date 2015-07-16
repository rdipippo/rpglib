package org.rpglib;

import org.deadsimple.mundungus.annotations.Collection;
import org.rpglib.persistence.GameState;

@Collection(name="GameState")
public class MyGameState extends GameState {
    protected int chutzpah;

    public int getChutzpah() {
        return chutzpah;
    }

    public void setChutzpah(int chutzpah) {
        this.chutzpah = chutzpah;
    }
}
