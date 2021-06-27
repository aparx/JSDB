package com.github.mindcubr.jsdb.deserialize.siege;

import com.google.gson.annotations.SerializedName;
import com.github.mindcubr.jsdb.deserialize.Alias;
import com.github.mindcubr.jsdb.deserialize.User;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;

/**
 * This class contains simple information about the user itself,
 * so about the registered person, as the nickname, id, avatar, etc.
 * <p>This class is part of the deserialization process
 * of the returning http post requests that are being used
 * to get the statistics of players and users.
 *
 * @author mindcubr
 * @since 1.0-0.1
 */
public class SiegePlayer extends User {

    @Deprecated
    @SerializedName("urlFriendlyNickname")
    private String urlFriendlyName;

    private String avatar;  //TODO: make it an image

    private String smallAvatar;

    //TODO
    private Alias[] aliases = new Alias[0];

    private String corsAvatar;

    private String authority;

    private String countryCode;

    @SerializedName("status")
    private SiegeUserStatus userStatus;

    private SiegeStats stats;

    public String getAvatar() {
        return avatar;
    }

    /**
     * Returns the {@link Alias aliases} the user had in history.
     */
    @NotNull
    public Alias[] getAliases() {
        return aliases;
    }

    public String getCorsAvatar() {
        return corsAvatar;
    }

    public String getAuthority() {
        return authority;
    }

    /**
     * Returns the country-code of this user, if possible.
     * <p>If the country-code is not known, the <em>statsdb</em>
     * API returns <em>false</em> as a value, that will be translated
     * into a null pointer and returned so.
     */
    public String getCountryCode() {
        synchronized (this) {
            //Whenever the country-code is undefined or equal to false
            //null is returned in this invoke.
            if ("false".equals(countryCode)) {
                //Update the country-code value
                return countryCode = null;
            }

            //Returns the actual proven country-code that seems to exist.
            return countryCode;
        }
    }

    public SiegeUserStatus getUserStatus() {
        return userStatus;
    }

    @Override
    public String toString() {
        return "User{" +
                "userID='" + getID() + '\'' +
                ", nickname='" + getName() + '\'' +
                ", urlFriendlyName='" + urlFriendlyName + '\'' +
                ", avatar='" + avatar + '\'' +
                ", smallAvatar='" + smallAvatar + '\'' +
                ", aliases=" + Arrays.toString(getAliases()) +
                ", corsAvatar='" + corsAvatar + '\'' +
                ", authority='" + authority + '\'' +
                ", countryCode=" + countryCode +
                ", userStatus=" + userStatus +
                ", stats=" + stats +
                '}';
    }

    /**
     * Updates this {@link #stats} attribute to
     * the input {@code stats} value.
     *
     * @apiNote It is recommended not to change the statistics class,
     * as the fetching process would be gone and wasteful.
     */
    public void setStats(@NotNull SiegeStats stats) {
        Objects.requireNonNull(stats);
        synchronized (this) {
            this.stats = stats;
        }
    }

    /**
     * Returns the main statistics of this user.
     */
    public SiegeStats getStats() {
        synchronized (this) {
            return stats;
        }
    }

}
