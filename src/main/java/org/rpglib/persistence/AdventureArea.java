package org.rpglib.persistence;

import org.bson.types.ObjectId;
import org.deadsimple.mundungus.annotations.Collection;
import org.deadsimple.mundungus.annotations.SubCollection;
import org.rpglib.calc.Outcome;
import org.rpglib.calc.Random;
import org.rpglib.persistence.GameState;

import java.util.List;

@Collection
public class AdventureArea {
    ObjectId id;

    @SubCollection(Encounter.class)
    List<Encounter> encounters;

    List<AdventureArea> subAreas;

    int turnCount;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public List<Encounter> getEncounters() {
        return encounters;
    }

    public Encounter chooseEncounter(GameState gs) {
        Outcome outcome = new Random().chooseOutcome(encounters);
        return (Encounter)outcome;
    }

    public void setEncounters(List<Encounter> encounters) {
        this.encounters = encounters;
    }

    public List<AdventureArea> getSubAreas() {
        return subAreas;
    }

    public void setSubAreas(List<AdventureArea> subAreas) {
        this.subAreas = subAreas;
    }

    public int getTurnCount() {
        return turnCount;
    }

    public void setTurnCount(int turnCount) {
        this.turnCount = turnCount;
    }
}
