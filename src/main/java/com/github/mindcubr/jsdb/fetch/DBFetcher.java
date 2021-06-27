package com.github.mindcubr.jsdb.fetch;

import com.github.mindcubr.jsdb.deserialize.User;
import com.github.mindcubr.jsdb.deserialize.siege.SiegePlayer;
import com.github.mindcubr.jsdb.exception.JSDBPlatformNotSupported;
import com.github.mindcubr.jsdb.exception.JSDBTokenInvalid;
import com.github.mindcubr.jsdb.exception.JSDBUserDoesNotExist;
import com.github.mindcubr.jsdb.fetch.http.DBResponse;
import com.github.mindcubr.jsdb.Game;
import com.github.mindcubr.jsdb.Platform;
import com.github.mindcubr.jsdb.bridge.DBBridge;
import com.github.mindcubr.jsdb.exception.JSDBFetchingException;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

/**
 * A DBFetcher is a parent class that is used to create fetcher,
 * that can fetch data and transform it into virtual instances
 * within the Java Cache. Those instances are used to store
 * and access values that can be used to, e.g. see through the statistics
 * of a certain User. This parent class is usually used as the
 * <em>global</em> extension for different games and is independent
 * from them. In other words, this class is used for global data
 * fetching of users, that contains abstract methods which content
 * can vary from game to game; that is why it is abstract and requires
 * multiple subclass versions of this parent fetcher, depending
 * on the game(s).
 *
 * @param <T> the type of {@link User} that is dependent on the game.
 * @author mindcubr
 * @since 1.0-0.1
 */
public abstract class DBFetcher<T extends User> implements IDBFetcher<T> {

    /**
     * The main-bridge-component
     */
    @NotNull
    private final DBBridge bridge;

    /**
     * The game that is represented and supported
     * in this fetcher instance.
     */
    @NotNull
    private final Game game;

    protected DBFetcher(@NotNull DBBridge bridge, @NotNull Game game) {
        this.bridge = Objects.requireNonNull(bridge);
        this.game = Objects.requireNonNull(game);
    }

    /**
     * Fetches an {@link SiegePlayer} instance from the input {@code url}.
     *
     * @param url the response being fetched from that URL
     * @return a new {@link SiegePlayer} instance from a response.
     * @throws JSDBFetchingException - if an exception occurs during the
     * fetching or requesting process.
     */
    public synchronized T fetchFromURL(@NotNull String url)
            throws JSDBFetchingException {
        try {
            //Fetch the response of the request
            return fetchPlayerFromResponse(bridge.request(url)
                    .json().auth().fetch());
        } catch (IOException exc) {
            throw new JSDBFetchingException(exc, url);
        }
    }

    /**
     * Returns all platforms that are supported by this
     * representing {@link #game}.
     *
     * @see #game
     * @see Game#getPlatforms()
     */
    public Platform[] getSupportedPlatforms() {
        return game.getPlatforms();
    }

    /**
     * Validates that the input {@code platform} is valid
     * and is supported by this {@link #game}.
     *
     * @param platform the platform to check for
     * @throws JSDBPlatformNotSupported if the {@code platform} is invalid
     * or not supported by this {@link #game}.
     * @see #game
     * @see JSDBPlatformNotSupported
     */
    public void checkPlatform(Platform platform) {
        if (!game.isPlatformSupported(platform)) {
            throw new JSDBPlatformNotSupported(game, platform);
        }
    }

    /**
     * Returns this {@link #game} value, that this subclass fetcher
     * instance is supporting and made for.
     *
     * @throws NullPointerException if the {@link #game} is null,
     * which never should be, but safe is safe
     */
    @NotNull
    public Game getGame() {
        return Objects.requireNonNull(game);
    }

    /**
     * Fetches a {@link SiegePlayer} from the given {@code response}.
     *
     * @return a new {@link SiegePlayer} instance made up off the {@code response}.
     * @throws JSDBFetchingException - if something went wrong during the
     * fetching, deserialization or tokenization.
     */
    public abstract T fetchPlayerFromResponse(@NotNull DBResponse response)
            throws JSDBFetchingException;

    /**
     * Fetches an {@link SiegePlayer} with the input {@code id}, if existing.
     *
     * @param id the id of the target user.
     * @return a possible new instance of the {@code id}.
     * @throws JSDBFetchingException - if the general fetching and data
     * collecting or deserialization went wrong
     * @throws JSDBUserDoesNotExist - if the target user does not exist
     * @throws JSDBTokenInvalid - if the given authorization failed
     */
    public abstract T fetchPlayerByID(@NotNull String id)
            throws JSDBUserDoesNotExist, JSDBFetchingException, JSDBTokenInvalid;

    /**
     * Fetches an {@link SiegePlayer} with the input {@code name}, if existing.
     *
     * @param platform the target platform to search for
     * @param name     the name of the target user.
     * @return a possible new instance of the {@code name}.
     * @throws JSDBFetchingException - if the general fetching and data
     * collecting or deserialization went wrong
     * @throws JSDBUserDoesNotExist - if the target user does not exist
     * @throws JSDBTokenInvalid - if the given authorization failed
     */
    public abstract T fetchPlayerByName(@NotNull Platform platform,
                                        final @NotNull String name)
            throws JSDBUserDoesNotExist, JSDBFetchingException, JSDBTokenInvalid;

}
