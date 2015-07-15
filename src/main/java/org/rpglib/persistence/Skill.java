package org.rpglib.persistence;

import org.bson.types.ObjectId;
import org.deadsimple.mundungus.EntityManager;
import org.deadsimple.mundungus.annotations.Collection;
import org.deadsimple.mundungus.annotations.SubCollection;
import org.rpglib.controller.RPGLib;

import java.util.HashMap;
import java.util.List;

@Collection
public abstract class Skill {
    ObjectId id;

    String name;

    String description;

    int minLevel;

    @SubCollection(CharacterClass.class)
    List<CharacterClass> allowedClasses;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMinLevel() {
        return minLevel;
    }

    public void setMinLevel(int minLevel) {
        this.minLevel = minLevel;
    }

    public abstract GameState useSkill(ObjectId gameStateId, HashMap<String, Object> parameters) throws NoSuchFieldException;

    public List<CharacterClass> getAllowedClasses() {
        return allowedClasses;
    }

    public void setAllowedClasses(List<CharacterClass> allowedClasses) {
        this.allowedClasses = allowedClasses;
    }
}
