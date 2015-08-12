package org.rpglib.persistence;

// Generated Sep 11, 2014 11:24:44 AM by Hibernate Tools 3.4.0.CR1

import org.bson.types.ObjectId;
import org.deadsimple.mundungus.annotations.Collection;
import org.deadsimple.mundungus.annotations.LoadType;
import org.deadsimple.mundungus.annotations.Reference;
import org.deadsimple.mundungus.annotations.SubCollection;

import java.util.List;

@Collection
public class Item {

	private ObjectId id;
	private String name;
	private String description;
	private GameState player;
	private ObjectId templateId;
	boolean equipped;
	Attack attack;
	
	@SubCollection(Attribute.class)
	private List<Attribute> equipAttributes = null;

	public Item() {
	}

	public Item(final ObjectId id, final String name, final String description, final boolean isDeleted) {
		this.id = id;
		this.name = name;
		this.description = description;
	}

	public Item(Item templateItem, GameState gs) {
		this.description = templateItem.getDescription();
		this.templateId = templateItem.getId();
		this.name = templateItem.getName();
		this.player = gs;
		this.equipAttributes = templateItem.getEquipAttributes();
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

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public List<Attribute> getEquipAttributes() {
		return this.equipAttributes;
	}

	public void setEquipAttributes(final List<Attribute> equipAttributes) {
		this.equipAttributes = equipAttributes;
	}

	@Reference(loadType = LoadType.LAZY)
	public GameState getPlayer() {
		return this.player;
	}

	public void setPlayer(final GameState player) {
		this.player = player;
	}

	public ObjectId getTemplateId() {
		return templateId;
	}

	public void setTemplateId(ObjectId templateId) {
		this.templateId = templateId;
	}

	public boolean isEquipped() {
		return equipped;
	}

	public void setEquipped(boolean equipped) {
		this.equipped = equipped;
	}

	public Attack getAttack() {
		return attack;
	}

	public void setAttack(Attack attack) {
		this.attack = attack;
	}

	@Override
	public boolean equals(final Object obj) {
		final Item item = (Item)obj;
		return item.getId().equals(this.getId());
	}
}
