package org.rpglib.persistence;

import org.bson.types.ObjectId;
import org.deadsimple.mundungus.annotations.Collection;
import org.deadsimple.mundungus.annotations.MapKey;

@Collection
public class PlayerAttribute {
    private ObjectId id;
        
    @MapKey
    private String name;
    
    Class<? extends GameState> playerClass;
        
    public PlayerAttribute(final String name) {
       this.name = name;
    }
    
    public ObjectId getId() {
        return this.id;
    }

    public void setId(final ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Class<? extends GameState> getPlayerClass() {
        return this.playerClass;
    }

    public void setPlayerClass(final Class<? extends GameState> playerClass) {
        this.playerClass = playerClass;
    }
}
