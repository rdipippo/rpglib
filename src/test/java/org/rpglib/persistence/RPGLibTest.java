package org.rpglib.persistence;

import org.junit.Assert;
import org.junit.Test;
import org.rpglib.MyGameState;
import org.rpglib.controller.RPGLib;

public class RPGLibTest {
    @Test
    public void testGetIntegerGameStateField() throws Exception {
        GameState gs = new GameState();
        gs.setBaseAttack(500);
        final Integer baseAttack = RPGLib.getNumericGameStateField(gs, "baseAttack");
        Assert.assertEquals(Integer.valueOf(500), baseAttack);
    }

    @Test
    public void testGetIntegerGameStateField_customGameState() throws Exception {
        MyGameState gs = new MyGameState();
        gs.setChutzpah(500);
        final Integer chutzpah = RPGLib.getNumericGameStateField(gs, "chutzpah");
        Assert.assertEquals(Integer.valueOf(500), chutzpah);
    }

    @Test
    public void testSetIntegerGameStateField() throws Exception {
        GameState gs = new GameState();
        RPGLib.setNumericGameStateField(gs, "baseAttack", 500);
        Assert.assertEquals(Integer.valueOf(500), Integer.valueOf(gs.getBaseAttack()));
    }

    @Test
    public void testSetIntegerGameStateField_customGameState() throws Exception{
        MyGameState gs = new MyGameState();
        RPGLib.setNumericGameStateField(gs, "chutzpah", 500);
        Assert.assertEquals(Integer.valueOf(500), Integer.valueOf(gs.getChutzpah()));
    }
}
