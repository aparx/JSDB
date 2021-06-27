package de.mindcubr.jsdb.bridge.config;

import de.mindcubr.jsdb.bridge.DBBridge;
import de.mindcubr.jsdb.bridge.DBToken;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;

/**
 * The general bridge configuration interface that contains method
 * and function calls that are used in a configuration context, usually
 * with a {@link DBBridge}. So the class this is used in, might store
 * mutable values containing the different variables while this interface
 * helps and provides Getters and Setters that enable the access onto
 * those variables and attributes.
 *
 * @author mindcubr
 * @since 1.0-0.1
 */
public interface IBridgeConfig {

    void setToken(@NotNull DBToken token);

    DBToken getToken();

    void setHttpClient(@NotNull OkHttpClient client);

    OkHttpClient getHttpClient();

}
