package de.mindcubr.jsdb.deserialize;

import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.Objects;

/**
 * Aliases are names that a {@link User} had in his history.
 * This name-history is essential for e.g. teams to track for,
 * as they might reveal older clan-names or names in general
 * that could be useful for them. Name-history is sometimes not
 * correct, as issues from the Ubisoft-API could occur.
 * Every name in the alias history contains the actual value
 * {@link #name} and {@link #timestamp}, when the alias was removed
 * or given, depending on the game they are used in.
 * Different games use different systems, which can cause compatibility
 * problems and some games could not display different aliases.
 * Many games even hide those aliases due to privacy contributions.
 *
 * @author mindcubr
 * @since 1.0-0.1
 */
public class Alias {

    @SerializedName("nickname")
    private String name;

    private long timestamp;

    /**
     * Returns the name of this alias.
     *
     * @throws NullPointerException - if this {@code name} is null.
     */
    @NotNull
    public String getName() {
        return Objects.requireNonNull(name);
    }

    /**
     * Returns the time when the alias has changed to
     * something different in <b>Unix time seconds</b>.
     *
     * @see <a href="https://en.wikipedia.org/wiki/Unix_time">https://en.wikipedia.org/wiki/Unix_time</a>
     */
    public long getTimeChanged() {
        return timestamp;
    }

    /**
     * Returns a {@link Date} out of the {@link #getTimeChanged() timestamp}
     * when this alias was changed (not created!).
     *
     * @see #getTimeChanged()
     */
    public Date getDateChanged() {
        return new Date(getTimeChanged() * 1000L /* 1000ms = 1s */);
    }

    @Override
    public String toString() {
        return "NameAlias{" +
                "name='" + name + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

}
