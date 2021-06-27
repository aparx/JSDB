package de.mindcubr.jsdb;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * General supported game platforms.
 *
 * @author mindcubr
 * @since 1.0-0.1
 */
public enum Platform {

    PC("pc"),
    XBOX("xbox"),
    PLAYSTATION("ps"),
    SWITCH("switch"),
    MOBILE("mobile"),

    /**
     * @deprecated Most developers don't count Stadia
     * as a different platform.
     */
    @Deprecated STADIA("stadia");

    @NotNull
    private final String alias;

    Platform(@NotNull String alias) {
        this.alias = Objects.requireNonNull(alias);
    }

    public String toShort() {
        return alias;
    }
}
