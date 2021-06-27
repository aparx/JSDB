package de.mindcubr.jsdb.fetch;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.mindcubr.jsdb.Game;
import de.mindcubr.jsdb.Globals;
import de.mindcubr.jsdb.Platform;
import de.mindcubr.jsdb.bridge.DBBridge;
import de.mindcubr.jsdb.deserialize.siege.SiegeStats;
import de.mindcubr.jsdb.deserialize.siege.SiegePlayer;
import de.mindcubr.jsdb.exception.JSDBFetchingException;
import de.mindcubr.jsdb.exception.JSDBTokenInvalid;
import de.mindcubr.jsdb.exception.JSDBUserDoesNotExist;
import de.mindcubr.jsdb.fetch.http.DBResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Subclass specified version of a {@link DBFetcher} made for the game
 * {@link de.mindcubr.jsdb.Game#SIEGE Rainbow Six: Siege}.
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
            throws JSDBFetchingException {
        Objects.requireNonNull(response);
        //The initial URL that caused the input response
        final String url = response.getCauser().getURL();

        try {
            final Gson gson = new Gson();
            final String content = response.getBodyContent();
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
            JsonObject payload = object.get("payload").getAsJsonObject();
            SiegePlayer user = gson.fromJson(payload.get("user"), SiegePlayer.class);
            user.setStats(gson.fromJson(payload.get("stats"), SiegeStats.class));
            return user;
        } catch (Exception exc) {
            //Rethrow the exc as an JSDBFetchingException
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
     * @throws JSDBFetchingException - if the user is invalid or the
     * fetching, requesting or server are invalid or offline.
     */
    @Override
    public synchronized SiegePlayer fetchPlayerByID(@NotNull String id)
            throws JSDBFetchingException {
        //Fetch the response of the request
        return fetchFromURL(String.format(Globals.URL_FETCH_ID, id));
    }

    /**
     * Fetches an {@link SiegePlayer} with the input {@code name}, if existing.
     *
     * @param platform the target platform to search for
     * @param name     the name of the target user.
     * @return a possible new instance of the {@code name}.
     * @throws JSDBFetchingException - if the user is invalid or the
     * fetching, requesting or server are invalid or offline.
     */
    @Override
    public synchronized SiegePlayer fetchPlayerByName(@NotNull Platform platform, final @NotNull String name)
            throws JSDBFetchingException {
        Objects.requireNonNull(platform);
        Objects.requireNonNull(name);
        Validate.notBlank(name);
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
     * @throws NullPointerException - if the {@code bridge} is null and
     * therefore undefined.
     */
    public static R6DBFetcher withBridge(@NotNull DBBridge bridge) {
        Objects.requireNonNull(bridge);
        return new R6DBFetcher(bridge);
    }

}
