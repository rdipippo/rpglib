package org.rpglib.persistence;

import org.bson.types.ObjectId;
import org.deadsimple.mundungus.annotations.Collection;
import org.deadsimple.mundungus.annotations.Transient;
import org.rpglib.controller.CombatController;
import org.rpglib.controller.RPGLib;
import org.rpglib.domain.GameMessage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

@Collection(name="Encounter")
public class CombatEncounter extends Encounter {

    ObjectId opponentTemplate;

    Combat combat;

    public ObjectId getOpponentTemplate() {
        return opponentTemplate;
    }

    public void setOpponentTemplate(ObjectId opponentTemplate) {
        this.opponentTemplate = opponentTemplate;
    }

    public Combat getCombat() {
        return combat;
    }

    public void setCombat(Combat combat) {
        this.combat = combat;
    }

    @Override
    @Transient
    public boolean isComplete(GameState newGameState) {
        return combat.isComplete();
    }

    @Override
    public GameState runEncounter(GameState gs) {
        ObjectId opponentTemplate = getOpponentTemplate();

        if (opponentTemplate != null) {
            Opponent opponent = new Opponent(opponentTemplate);
            RPGLib.entityManager().persist(opponent);

            Combat combat = new Combat(gs, opponent);
            setCombat(combat);
            gs.setEncounter(this);
            RPGLib.entityManager().persist(gs);

            CombatController combatController = new CombatController();
            gs = combatController.combatRound(gs.getId());
        }

        if (getCombat().isPlayerWon()) {
            collectRewards(gs);
        }

        RPGLib.entityManager().persist(gs);
        return gs;
    }

    @Override
    public GameState continueEncounter(GameState gs) {
        Combat combat = getCombat();

        if (gs.getCommand() != null && gs.getCommand().getDisplayText().equals("Continue Attack")) {
            CombatController combatController = new CombatController();
            gs = combatController.combatRound(gs.getId());

            if (combat.isPlayerWon()) {
                collectRewards(gs);
            }
        } else {
            combat.setComplete(true);
            combat.setPlayerWon(false);
            gs.addMessage(GameMessage.PLAYER_FLED_COMBAT.format());
        }

        return gs;
    }

    @Override
    public List<PlayerCommand> getCommands() {
        List<PlayerCommand> commands = new ArrayList<PlayerCommand>();

        commands.add(new PlayerCommand("Flee"));
        commands.add(new PlayerCommand("Continue Attack"));

        return commands;
    }
}
