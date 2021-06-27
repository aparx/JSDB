package com.github.mindcubr.jsdb;

import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Enumeration that contains the different games supported
 * by the <em>StatsDB</em> Developer-API.
 *
 * @author mindcubr
 * @apiNote When this framework will run out of updates,
 * you can manipulate the amount of games by manipulating this
 * enumeration {@link #values() values} array via
 * <a href="https://www.oracle.com/technical-resources/articles/java/javareflection.html">Reflections</a>
 * in Java 1.8.
 * @see <a href="https://www.oracle.com/technical-resources/articles/java/javareflection.html">https://www.oracle.com/technical-resources/articles/java/javareflection.html</a>
 * @since 1.0-0.1
 */
public enum Game {

    SIEGE("r6s", "Rainbow Six Siege",
            Platform.PC,
            Platform.XBOX,
            Platform.PLAYSTATION);

    @NotNull
    private final String shortcut, name;

    protected Platform[] platforms;

    Game(@NotNull String shortcut, @NotNull String name, @NotNull Platform... platforms) {
        this.shortcut = Objects.requireNonNull(shortcut);
        this.name = Objects.requireNonNull(name);
        this.platforms = platforms == null ?
                new Platform[0] : platforms;
    }

    /**
     * Returns all platforms supported by this game.
     *
     * @apiNote This {@link #platforms} attribute is mutable,
     * so you can change the amount of supported platforms if required.
     * It is not recommended but still useful, as the support for this
     * library might end one day and platforms changing.
     */
    public Platform[] getPlatforms() {
        return platforms;
    }

    /**
     * Returns whether the input {@code platform} is a supported
     * platform of the game this fetcher is supporting and therefore
     * representing.
     *
     * @param platform the target platform to check for
     * @return whether the input {@code platform} is supported by
     * this game.
     */
    public boolean isPlatformSupported(Platform platform) {
        if (platform == null)
            return false;
        return ArrayUtils.contains(getPlatforms(), platform);
    }

    @NotNull
    public String getShortcut() {
        return shortcut;
    }

    @NotNull
    public String getName() {
        return name;
    }
}
