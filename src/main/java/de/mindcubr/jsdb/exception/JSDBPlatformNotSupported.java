package de.mindcubr.jsdb.exception;

import de.mindcubr.jsdb.Game;
import de.mindcubr.jsdb.Platform;
import org.jetbrains.annotations.NotNull;

/**
 * Exception usually thrown when a certain {@link Platform}
 * is not compatible with a given {@link Game}.
 *
 * @author mindcubr
 * @since 1.0-0.2
 */
public class JSDBPlatformNotSupported extends RuntimeException {

    @NotNull
    private final Game game;

    @NotNull
    private final Platform platform;

    public JSDBPlatformNotSupported(@NotNull Game game, @NotNull Platform platform) {
        super("The platform '" + platform + "' is not compatible with the game '" + game.name() + "'.");
        this.game = game;
        this.platform = platform;
    }

}
