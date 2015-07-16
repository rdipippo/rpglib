package org.rpglib.persistence;

import org.junit.Assert;
import org.junit.Test;
import org.rpglib.controller.TurnController;
import org.rpglib.controller.bootstrap.SkillController;

public class SkillControllerTest extends BaseTest {
    @Test
    public void testCombatSkill() throws Exception {
        AdventureArea area = getAdventureArea(true);
        TurnController tc = new TurnController();
        GameState currentGS = tc.takeTurn(getGameState().getId(), area.getId());

        CombatSkill cs = new CombatSkill();
        cs.setMinDamage(2);
        cs.setMaxDamage(2);
        em().persist(cs);

        Opponent opponent = em().find(Opponent.class, currentGS.getEncounter().getCombat().getOpponentId());
        int opponentHealth = opponent.getHealth();

        SkillController sc = new SkillController();
        sc.useSkill(cs.getId(), currentGS.getId(), null);

        opponent = em().find(Opponent.class, currentGS.getEncounter().getCombat().getOpponentId());

        Assert.assertEquals(opponentHealth - 2, opponent.getHealth());
    }

    @Test
    public void testBuffSkill() throws NoSuchFieldException {
        Effect effect = new Effect();
        effect.setNumRounds(3);
        effect.setName("Made of win");
        effect.setFieldName("attack");
        effect.setModifier("100%");
        em().persist(effect);

        BuffSkill skill = new BuffSkill();
        skill.setEffect(effect);
        em().persist(skill);

        SkillController sc = new SkillController();
        GameState gameState = sc.useSkill(skill.getId(), getGameState().getId(), null);
        Assert.assertEquals(100, gameState.getAttack());

        TurnController tc = new TurnController();
        AdventureArea area = getAdventureArea(false);

        GameState gs = tc.takeTurn(gameState.getId(), area.getId());
        Assert.assertEquals(2, gs.getEffects().get(0).getNumRounds());
        Assert.assertEquals(100, gameState.getAttack());

        gs = tc.takeTurn(gameState.getId(), area.getId());
        Assert.assertEquals(1, gs.getEffects().get(0).getNumRounds());
        Assert.assertEquals(100, gameState.getAttack());

        gs = tc.takeTurn(gameState.getId(), area.getId());
        Assert.assertEquals(0, gs.getEffects().size());
        Assert.assertEquals(50, gs.getAttack());
    }
}
