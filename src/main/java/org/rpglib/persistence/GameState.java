package org.rpglib.persistence;

// Generated Sep 11, 2014 11:24:44 AM by Hibernate Tools 3.4.0.CR1

import org.bson.types.ObjectId;
import org.deadsimple.mundungus.annotations.Collection;
import org.rpglib.calc.ModifierCalculator;
import org.rpglib.combat.Combatant;
import org.rpglib.controller.RPGLib;
import org.rpglib.domain.GameMessage;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Collection
public class GameState implements Combatant {
	private ObjectId id;

	private String name;

	private int baseAttack;

	private int baseDefense;

	private int baseHealth;

	private int baseMana;

    private int attack;

    private int mana;

    private int defense;

    private int health;

    private int level;

    private long experiencePoints;

    private int money;

    private int remainingTurns;

    private Encounter encounter;

	private Boolean isDeleted;

    private List<Attack> attacks;

    private Attack currentAttack;

    private List<String> messages;

    private List<Effect> effects;

    private PlayerCommand command;
    
	public GameState() {
		// Auto-generated constructor stub
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
	
	public int getBaseAttack() {
        return this.baseAttack;
    }

    public void setBaseAttack(final int baseAttack) {
        this.baseAttack = baseAttack;
    }

	public int getBaseDefense() {
        return this.baseDefense;
    }

    public void setBaseDefense(final int baseDefense) {
        this.baseDefense = baseDefense;
    }

    public int getBaseHealth() {
        return this.baseHealth;
    }

    public void setBaseHealth(final int baseHealth) {
        this.baseHealth = baseHealth;
    }

    public int getBaseMana() {
        return this.baseMana;
    }

    public void setBaseMana(final int baseMana) {
        this.baseMana = baseMana;
    }

	public Boolean isIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(final Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

    public List<Attack> getAttacks() {
        return attacks;
    }

    public void setAttacks(List<Attack> attacks) {
        this.attacks = attacks;
    }

    public Attack getCurrentAttack() {
        return currentAttack;
    }

    public void setCurrentAttack(Attack attack) {
        this.currentAttack = attack;
    }

    public int getRemainingTurns() {
        return remainingTurns;
    }

    public void setRemainingTurns(int remainingTurns) {
        this.remainingTurns = remainingTurns;
    }

    public void takeDamage(int damage) {
        this.health -= damage;
    }

    @Override
    public int getAttack() {
        return attack;
    }

    @Override
    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    @Override
    public int getDefense() {
        return defense;
    }

    @Override
    public void setDefense(int defense) {
        this.defense = defense;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void setHealth(int health) {
        this.health = health;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public GameState spendMoney(int amount) {
        money -= amount;
        return this;
    }

    public Encounter getEncounter() {
        return encounter;
    }

    public void setEncounter(Encounter encounter) {
        this.encounter = encounter;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public long getExperiencePoints() {
        return experiencePoints;
    }

    public void setExperiencePoints(long experiencePoints) {
        this.experiencePoints = experiencePoints;
    }

    public List<Effect> getEffects() {
        return effects;
    }

    public void setEffects(List<Effect> effects) {
        this.effects = effects;
    }

    public PlayerCommand getCommand() {
        return command;
    }

    public void setCommand(PlayerCommand command) {
        this.command = command;
    }

    public boolean checkForLevelUp() {
        return experiencePoints > ((level * level) * 100);
    }

    public void levelUp() {
        int currentLevel = getLevel();
        setLevel(++currentLevel);
        addMessage(GameMessage.PLAYER_LEVELED_UP.format(getLevel()));
        RPGLib.entityManager().persist(this);
    }

    public void addMessage(String msg) {
        if (messages == null) {
            messages = new ArrayList<String>();
        }

        messages.add(msg);
    }

    public GameState applyModifier(Attribute modifier, boolean unapply) throws Exception {
        new ModifierCalculator(this, modifier.getName(), modifier.getValue(), unapply).apply();

        return this;
    }

    public GameState addEffect(Effect effect) throws NoSuchFieldException {
        if (effects == null) {
            effects = new ArrayList<Effect>();
        }

        effects.add(effect);
        effect.apply(this);
        RPGLib.entityManager().persist(this);
        return this;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public List<String> getMessages() {
        return messages;
    }
}
