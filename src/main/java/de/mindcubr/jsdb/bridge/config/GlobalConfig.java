package de.mindcubr.jsdb.bridge.config;

import de.mindcubr.jsdb.bridge.DBToken;
import okhttp3.OkHttpClient;
import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * A {@link IBridgeConfig} implementation, that is used to store
 * important authorization and global values that will be required
 * in future actions caused by, e.g. different {@linkplain
 * de.mindcubr.jsdb.bridge.DBBridge bridges}.
 * Default {@link IBridgeConfig} mutable integration, where
 * every attribute is mutable and thread-safe updatable.
 *
 * @author mindcubr
 * @see IBridgeConfig
 * @since 1.0-0.1
 */
public class GlobalConfig implements IBridgeConfig {

    private static final String ERR_TOKEN_NOT_SET = "The configured token is invalid or null.";

    /**
     * A thread-safe volatile attribute used to describe the token
     * of authorization used whenever post requests to the
     * actual <em>StatsDB</em> website are performed.
     */
    @NotNull
    protected volatile DBToken token;

    /**
     * The main {@link OkHttpClient} of the okhttp3-library
     * used to e.g. make post requests to the <em>StatsDB</em>.
     */
    @NotNull
    protected OkHttpClient client = new OkHttpClient();

    private GlobalConfig() {
        ;
    }

    /**
     * Updates this {@link #token} attribute to the
     * input {@code token} and validates it.
     *
     * @param token the token used to update the attribute
     * @throws NullPointerException - if the {@code token} is null.
     * @throws IllegalArgumentException - if the {@code token} is invalid.
     */
    @Override
    public void setToken(@NotNull DBToken token) {
        Objects.requireNonNull(token);
        token.validate();
        synchronized (this) {
            this.token = token;
        }
    }

    /**
     * Returns this {@link #token}.
     *
     * @throws NullPointerException - if the {@code token} is null.
     */
    @Override
    public DBToken getToken() {
        synchronized (this) {
            Validate.notNull(token, ERR_TOKEN_NOT_SET);
            return token;
        }
    }

    /**
     * Returns the main http client used for every bridge request
     * sent from here.
     */
    @NotNull
    @Override
    public OkHttpClient getHttpClient() {
        return client;
    }

    /**
     * Updates this {@link #client} value to the input {@code client}.
     * <p>The <em>client</em> is used as the dispatcher, or agent,
     * used to send post requests over the internet.
     *
     * @param client the client to be updated, not null
     */
    @Override
    public void setHttpClient(@NotNull OkHttpClient client) {
        this.client = Objects.requireNonNull(client);
    }

    /**
     * Creates a new {@link GlobalConfig} instance and sets
     * the token to the given input {@code token}.
     * <p>This method should be the default creation method of a
     * {@link GlobalConfig}, as a {@link DBToken} is preset and
     * created by default. This is much safer than using the {@link #empty()}
     * method, where no token nor important authorization is set by default,
     * which can cause issues and compatibility problems with different
     * actions performed by, e.g. a {@link de.mindcubr.jsdb.bridge.DBBridge}.
     *
     * @param token the new token value contained in the returned instance.
     * @return a new {@link GlobalConfig} instance.
     * @see #setToken(DBToken)
     */
    public static GlobalConfig withToken(@NotNull DBToken token) {
        GlobalConfig config = new GlobalConfig();
        config.setToken(token);
        return config;
    }

    /**
     * Returns a new {@link GlobalConfig} instance without any
     * {@linkplain DBToken} set by default.
     * <p>Preferring this method over the {@link #withToken(DBToken)}
     * method can cause problems when trying to use the configuration
     * with a {@link de.mindcubr.jsdb.bridge.DBBridge}.
     *
     * @return a new {@link GlobalConfig} instance with no token or settings
     * preset, by default.
     * @apiNote Generally it is recommended to use {@link #withToken(DBToken)}
     * as it is the safer way to directly implement a {@link DBToken}.
     * This method should only be used, whenever the token can only be created
     * <em>after</em> the {@link GlobalConfig} was initialized.
     * If this is not the case, using the {@link #withToken(DBToken)} method
     * makes much more sense and is way safer.
     */
    public static GlobalConfig empty() {
        return new GlobalConfig();
    }

    //TODO more then withToken ... (.empty() ?)

}
