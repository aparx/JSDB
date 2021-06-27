package de.mindcubr.jsdb.fetch.http;

import de.mindcubr.jsdb.bridge.DBBridge;
import de.mindcubr.jsdb.bridge.DBToken;
import de.mindcubr.jsdb.bridge.config.IBridgeConfig;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

/**
 * Extended {@link Request.Builder} instance which is used to create
 * advanced and simple http requests. This class makes advantage of that
 * and adds utility that expands the raw builder and connects this
 * framework to it.
 *
 * @author mindcubr
 * @see Request.Builder
 * @since 1.0-0.1
 */
public class DBRequest {

    /**
     * Error message thrown whenever an empty, or in general blank,
     * URL is passed to the request.
     */
    private static final String ERR_URL_EMPTY = "The URL must not be empty or blank.";

    private static final String ERR_REQUEST_NOT_BEGUN = "The request target bridge must be set before executing.";

    private static final String ERR_BUILDER_UNDEFINED = "The actual http building process must not be null.";

    /**
     * The target URL of this request.
     */
    @NotNull
    private final String url;

    /**
     * The main-bridge-component.
     */
    private DBBridge bridge;

    @NotNull
    private final Request.Builder builder;

    protected DBRequest(@NotNull final String url, @NotNull Request.Builder builder) {
        this.url = url;
        this.builder = Objects.requireNonNull(builder);
    }

    /**
     * Creates a new {@link DBRequest} with the target {@code url}
     * that is passed.
     *
     * @param url the target url of the new request
     * @return the new {@link DBRequest} with that target {@code url}.
     * @apiNote It is recommended to rather use {@link #request(String, DBBridge)}
     * as the important {@link #connect(DBBridge)} part and method invocation is
     * not necessary anymore. It might save you time and is much more safe
     * than this method.
     * @see #request(String, DBBridge)
     * @see #connect(DBBridge)
     */
    public static DBRequest request(@NotNull final String url) {
        Validate.notBlank(url, ERR_URL_EMPTY);
        return new DBRequest(Objects.requireNonNull(url), new Request.Builder());
    }

    /**
     * Creates a new {@link DBRequest} with the target {@code url}
     * that is passed and {@link #connect(DBBridge) connects} it directly.
     * <p>So the important first method {@link #connect(DBBridge)} is invoked
     * directly after the instance is created. The result is returned.
     *
     * @param url the target url of the new request
     * @return the new {@link DBRequest} with that target {@code url}.
     * @see #connect(DBBridge) connect(DBBridge) is automatically invoked and returned.
     */
    public static DBRequest request(@NotNull final String url, @NotNull final DBBridge bridge) {
        return request(url).connect(bridge);
    }

    /**
     * Sets the {@link DBBridge main-bridge} of this request, that contains
     * further information about how the request should be sent and used.
     * <p>By invoking this method, not only the bridge but this {@link #url}
     * is updated via this {@link Request.Builder#url(URL)} function.
     * <p>This method is the first step for the request to be fetched,
     * executed and sent, after the initial construction.
     *
     * @param bridge the target bridge.
     * @return this instance for an easier development flow.
     * @throws NullPointerException - if {@code bridge} is null.
     * @apiNote This method should be called first, after the construction
     * of this instance. If not, issues could appear (by time).
     * @see Request.Builder#url(URL)
     */
    public DBRequest connect(@NotNull DBBridge bridge) {
        synchronized (this) {
            this.bridge = Objects.requireNonNull(bridge);
            builder.url(url);
            return this;
        }
    }

    /**
     * Creates a new post request to this {@link #url} with
     * authorizing the default {@link #bridge} components, that
     * are required for this typical database post-request.
     *
     * @return the response of that request.
     * @throws IllegalStateException – when the call has already been executed.
     * @throws NullPointerException - if this {@link #builder} or {@link #bridge} is null.
     * @apiNote Make sure, that every other required method is called
     * first. The {@link #connect(DBBridge)} method must be already
     * successfully invoked at least once.
     * @see IBridgeConfig#getHttpClient()
     * @see Request.Builder#build()
     * @see OkHttpClient#newCall(Request)
     */
    public DBResponse fetch() throws IOException {
        synchronized (this) {
            Validate.notNull(builder, ERR_BUILDER_UNDEFINED);
            Validate.notNull(bridge, ERR_REQUEST_NOT_BEGUN);
            OkHttpClient client = bridge.getConfig().getHttpClient();
            Request request = builder.build();
            return DBResponse.of(client.newCall(request).execute(), this);
        }
    }


    /**
     * Updates the {@code name} header value of this request
     * to the given {@code value}.
     *
     * @return this instance for an easier development flow.
     * @throws NullPointerException - if this {@link #builder} or {@code name} is null.
     * @apiNote The exception throw should not happen, as it is unit tested.
     */
    public DBRequest header(@NotNull String name, String value) {
        Validate.notNull(name);
        synchronized (this) {
            Validate.notNull(builder, ERR_BUILDER_UNDEFINED);
            builder.header(name, value);
            return this;
        }
    }

    /**
     * Updates the accepting content header to <code>application/json</code>.
     *
     * @return this instance for an easier development flow.
     * @throws NullPointerException - if this {@link #builder} is null.
     * @apiNote The exception throw should not happen, as it is unit tested.
     * @see #header(String, String)
     */
    public DBRequest json() {
        return header("accept", "application/json");
    }

    /**
     * Authenticates the token given in the passed {@link #bridge},
     * by updating the <code>Authentication</code> header in the URL
     * request;
     * <p><code>Authorization: Basic {token}</code>
     *
     * @throws NullPointerException - if this {@link #builder} or {@link #bridge} is null.
     * @throws IllegalArgumentException - if this {@link #bridge} {@link IBridgeConfig#getToken()} is invalid.
     * @see #header(String, String)
     */
    public DBRequest auth() {
        synchronized (this) {
            Validate.notNull(bridge, ERR_REQUEST_NOT_BEGUN);
            DBToken token = bridge.getConfig().getToken();
            token.validate();
            header("Authorization", "Basic " + token.getToken());
            return this;
        }
    }

    /**
     * Returns this {@link #builder}, not null
     *
     * @apiNote If you find a method that should be contained
     * in this {@link DBRequest} class, let us know on our GitHub.
     * @deprecated Using the {@link Request.Builder} methods,
     * such as {@link Request.Builder#url(URL)} will not mutate
     * this {@link #url} attributes. So using those methods is
     * not recommended, but can be done if required.
     */
    @NotNull
    @Deprecated
    public Request.Builder getBuilder() {
        synchronized (this) {
            return builder;
        }
    }

    /**
     * Returns this initial {@link #url}.
     */
    @NotNull
    public String getURL() {
        return url;
    }

}
