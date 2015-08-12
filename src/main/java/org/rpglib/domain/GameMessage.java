package org.rpglib.domain;

import java.text.MessageFormat;

public enum GameMessage {
    ITEM_ALREADY_EQUIPPED("Item {0} is already equipped."),
    ITEM_NOT_EQUIPPED("Item {0} is not equipped."),
    ITEM_IS_NOT_YOURS("Item {0} does not belong to you."),
    ITEM_NOT_EQUIPPABLE("Item {0} is not equippable."),
    MISSED("{0} missed!"),
    HIT("{0} hit for {1} damage!"),
    PLAYER_WON_COMBAT("You won!"),
    PLAYER_LOST_COMBAT("You lost. Better luck next time!"),
    PLAYER_GOT_STAT_REWARD("You got {0} {1}."),
    PLAYER_LEVELED_UP("You leveled up to level {0}. Congratulations!"),
    PLAYER_GOT_ITEM("You found a {0}. Enjoy"),
    PLAYER_FLED_COMBAT("You ran away. Coward!"),
    NOT_ENOUGH_MONEY("That costs {0} and you only have {1}.")
    ;

    String message;

    String messageTemplate;

    private GameMessage(final String messageTemplate) {
        this.messageTemplate = messageTemplate;
        this.message = messageTemplate;
    }
    
    public String getMessage() {
        return this.message;
    }
    
    public int getErrorCode() {
        return this.ordinal();
    }
    
    public String format(final Object ... params) {
        return MessageFormat.format(this.messageTemplate, params);
    }
}
