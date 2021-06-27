package com.github.mindcubr.jsdb.fetch;

import com.github.mindcubr.jsdb.deserialize.siege.SiegePlayer;
import com.github.mindcubr.jsdb.deserialize.siege.SiegeStats;
import com.github.mindcubr.jsdb.exception.JSDBPlatformNotSupported;
import com.github.mindcubr.jsdb.exception.JSDBTokenInvalid;
import com.github.mindcubr.jsdb.exception.JSDBUserDoesNotExist;
import com.github.mindcubr.jsdb.fetch.http.DBResponse;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.github.mindcubr.jsdb.Game;
import com.github.mindcubr.jsdb.Globals;
import com.github.mindcubr.jsdb.Platform;
import com.github.mindcubr.jsdb.bridge.DBBridge;
import com.github.mindcubr.jsdb.exception.JSDBFetchingException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Subclass specified version of a {@link DBFetcher} made for the game
 * {@link Game#SIEGE Rainbow Six: Siege}.
 * <p>This fetcher is a specialized version of the global
 * {@link DBFetcher} which can be used for different games,
 * whereas this class only contains methods that can fetch
 * data and responses from the game named above.
 *
 * @author mindcubr
 * @see <a href="https://en.wikipedia.org/wiki/Tom_Clancy%27s_Rainbow_Six_Siege">https://en.wikipedia.org/wiki/Tom_Clancy%27s_Rainbow_Six_Siege</a>
 * @since 1.0-0.1
 */
public class R6DBFetcher extends DBFetcher<SiegePlayer> {

    private R6DBFetcher(@NotNull DBBridge bridge) {
        super(bridge, Game.SIEGE);
    }

    /**
     * Fetches a {@link SiegePlayer} from the given {@code response}.
     *
     * @return a new {@link SiegePlayer} instance made up off the {@code response}.
     * @throws JSDBFetchingException - if the requesting or fetching goes wrong.
     */
    @Override
    public synchronized SiegePlayer fetchPlayerFromResponse(@NotNull DBResponse response)
            throws JSDBTokenInvalid, JSDBUserDoesNotExist, JSDBFetchingException {
        Objects.requireNonNull(response);
        //The initial URL that caused the input response
        final String url = response.getCauser().getURL();
        final Gson gson = new Gson();
        final String content = response.getContent();
        final JsonElement element = JsonParser.parseString(content);
        final JsonObject object = element.getAsJsonObject();
        final int code = response.getCode();

        //Validate that the servers are online and the URL correct
        Validate.isTrue(object.has("code"),
                "The target URL is offline or invalid.");

        //Throw an external exception if the user does not exist
        if (code == 404) {
            throw new JSDBUserDoesNotExist(url, filterUsernameFromURL(url));
        }

        //Throw an external exception if the token and authorization
        //is invalid and access forbidden
        if (code == 401) {
            throw new JSDBTokenInvalid(StringUtils.EMPTY);
        }

        //Validating that there is a payload attached to it
        Validate.isTrue(object.has("payload"),
                code + ':' + object.get("message").getAsString());

        //Getting the information off that payload
        try {
            JsonObject payload = object.get("payload").getAsJsonObject();
            SiegePlayer user = gson.fromJson(payload.get("user"), SiegePlayer.class);
            user.setStats(gson.fromJson(payload.get("stats"), SiegeStats.class));
            return user;
        } catch (Exception exc) {
            throw new JSDBFetchingException(exc, url);
        }
    }

    /**
     * Returns an username filtered from the input {@code url}.
     * <p>If the {@code url} is empty or null, an empty
     * string is returned.
     * <p>The last post slash word is used as the actual username,
     * while the rest of the URL is filtered out. So if the URL
     * would be equal to this:
     * <pre><code>
     *     https://api.statsdb.net/r6/pc/player/testusername
     * </code></pre>
     * <p>The last word <tt>testusername</tt> would be used
     * as the actual username and returned in this instance.
     *
     * @param url the target url to filter the username out of
     * @return the filtered and clear username off that URL
     * @apiNote This is an unsafe way for future games that could
     * contain specialized characters. So this is only applicable
     * as long as Ubisoft only allows alphanumeric characters without
     * any white space.
     * @since 1.0-0.1
     * @deprecated Future versions of this library might actually
     * include the username searching in their post request parameters.
     * (Read the apiNote for more information about why this method is
     * deprecated and should not be used in long-living applications.)
     */
    //TODO: Change this to an actual post request
    // parameter or header instead of filtering as it might cause
    // compatibility problems
    @Deprecated
    public String filterUsernameFromURL(final String url) {
        if (StringUtils.isBlank(url))
            return null;

        //Filters the name at the end of the URL.
        return url.substring(url.lastIndexOf('/') + 1);
    }

    /**
     * Fetches an {@link SiegePlayer} with the input {@code id}, if existing.
     *
     * @param id the id of the target user.
     * @return a possible new instance of the {@code id}.
     * @throws JSDBFetchingException if the general fetching and data
     * collecting or deserialization went wrong.
     * @throws JSDBUserDoesNotExist if the target user does not exist.
     * @throws JSDBTokenInvalid if the given authorization failed.
     */
    @Override
    public synchronized SiegePlayer fetchPlayerByID(@NotNull String id)
            throws JSDBUserDoesNotExist, JSDBFetchingException, JSDBTokenInvalid {
        //Fetch the response of the request
        return fetchFromURL(String.format(Globals.URL_FETCH_ID, id));
    }

    /**
     * Fetches an {@link SiegePlayer} with the input {@code name}, if existing.
     *
     * @param platform the target platform to search for
     * @param name     the name of the target user.
     * @return a possible new instance of the {@code name}.
     * @throws JSDBFetchingException if the general fetching and data
     * collecting or deserialization went wrong.
     * @throws JSDBUserDoesNotExist if the target user does not exist.
     * @throws JSDBPlatformNotSupported if the target {@code platform} is not
     * compatible with this game.
     * @throws JSDBTokenInvalid if the given authorization failed.
     */
    @Override
    public synchronized SiegePlayer fetchPlayerByName(@NotNull Platform platform, final @NotNull String name)
            throws JSDBUserDoesNotExist, JSDBFetchingException, JSDBPlatformNotSupported, JSDBTokenInvalid {
        Objects.requireNonNull(platform);
        Objects.requireNonNull(name);
        Validate.notBlank(name);
        checkPlatform(platform);

        //Fetch the response of the request
        return fetchFromURL(String.format(Globals.URL_FETCH_USER,
                platform.toShort(), name));
    }

    /**
     * Creates a new {@link R6DBFetcher} instance including the
     * {@code bridge} as a main component link.
     *
     * @param bridge the bridge given to the fetcher
     * @return a new instance of {@link R6DBFetcher}.
     * @throws NullPointerException if the {@code bridge} is null and
     * therefore undefined.
     */
    public static R6DBFetcher withBridge(@NotNull DBBridge bridge) {
        Objects.requireNonNull(bridge);
        return new R6DBFetcher(bridge);
    }

}
