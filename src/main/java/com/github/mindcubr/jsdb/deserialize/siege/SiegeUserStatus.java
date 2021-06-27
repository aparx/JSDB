package com.github.mindcubr.jsdb.deserialize.siege;

/**
 * @author mindcubr
 * @since 1.0-0.1
 */
public class SiegeUserStatus {

    /**
     * The type of the game.
     */
    private int type;

    /**
     * Boolean that determines whether the user
     * is currently engaged in a game.
     */
    private boolean game;

    /**
     * Returns whether the player is currently engaged
     * in a game or mission.
     */
    public boolean isIngame() {
        return game;
    }

    //TODO: what number is what mode?
    /**
     * Returns the game mode the player {@link #isIngame() is
     * potentially} engaged with.
     * <p>0 = the player is not in any game mode.
     *
     * @see #isIngame()
     */
    public int getMode() {
        return type;
    }

    @Override
    public String toString() {
        return "R6UserStatus{" +
                "type=" + type +
                ", game=" + game +
                '}';
    }

}
