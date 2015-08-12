package org.rpglib.persistence;

public class PlayerCommand {
    String displayText;

    public PlayerCommand(String displayText) {
        setDisplayText(displayText);
    }


    public PlayerCommand() {
        // used by pf
    }

    public String getDisplayText() {
        return displayText;
    }

    public void setDisplayText(String displayText) {
        this.displayText = displayText;
    }
}
