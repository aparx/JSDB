package de.mindcubr.jsdb.deserialize;

import com.google.gson.annotations.SerializedName;
import de.mindcubr.jsdb.Game;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * No description available.
 *
 * @author mindcubr
 * @since 1.0-0.1
 */
public class User {

    private String id;

    @SerializedName("nickname")
    private String name;

    /**
     * Returns the default user ID of this user instance.
     */
    public String getID() {
        return id;
    }

    /**
     * Returns the nickname of this user.
     *
     * @throws NullPointerException - if the {@code nickname} is null
     * and therefore undefined.
     */
    @NotNull
    public String getName() {
        return Objects.requireNonNull(name);
    }

}
