package com.github.mindcubr.jsdb.bridge;


import com.github.mindcubr.jsdb.bridge.config.GlobalConfig;
import com.github.mindcubr.jsdb.bridge.config.IBridgeConfig;
import com.github.mindcubr.jsdb.fetch.http.DBRequest;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * The <b>Bridge</b> is the main component and access point to the
 * statistics, requests, methods and collections of data regarding
 * the <em>statsdb.net</em> application.
 * <p>In other words, this class is the main component that connects
 * many classes with each other in order to provide a simple access point
 * to other classes and their features, which may require other information
 * also provided in this "net" of structure.
 *
 * @author mindcubr
 * @since 1.0-0.1
 */
public class DBBridge {

    @NotNull
    private IBridgeConfig config;

    private DBBridge(@NotNull IBridgeConfig config) {
        this.config = config;
    }

    /**
     * Creates a general {@link DBRequest} with input {@code url}
     * as URL target for the http post request and {@link DBRequest#connect(DBBridge)}
     * connects this bridge onto that {@linkplain DBRequest request instance};
     * <p><code>{@link DBRequest#request(String, DBBridge)}</code>
     *
     * @param url the target url for the wanted request
     * @return a new {@link DBRequest} instance.
     * @see DBRequest#request(String, DBBridge)
     */
    public DBRequest request(@NotNull final String url) {
        return DBRequest.request(url, this);
    }

    /**
     * Returns this {@link #config} value.
     *
     * @throws NullPointerException - if this {@code config} is null.
     * @apiNote The {@link NullPointerException} should not occur, as the
     * mutation is watched by unit tests and is embedded with fail safeties.
     * But still; safe is safe.
     */
    @NotNull
    public IBridgeConfig getConfig() {
        return Objects.requireNonNull(config);
    }

    /**
     * Updates this {@link #config} to the input {@code config}
     * instance, if not null.
     *
     * @param config the target configuration instance.
     * @throws NullPointerException - if the input {@code config} is null.
     */
    public void setConfig(@NotNull GlobalConfig config) {
        this.config = Objects.requireNonNull(config);
    }

    /**
     * Creates a new instance of a {@link DBBridge}.
     *
     * @param config the configuration of the bridge
     * @return a new {@link DBBridge} with the input {@code config}
     */
    public static DBBridge create(@NotNull GlobalConfig config) {
        Objects.requireNonNull(config);
        return new DBBridge(config);
    }

}
