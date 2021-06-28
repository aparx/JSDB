package io.github.mindcubr.jsdb.fetch;

import io.github.mindcubr.jsdb.deserialize.User;
import io.github.mindcubr.jsdb.fetch.http.DBResponse;
import io.github.mindcubr.jsdb.exception.JSDBFetchingException;
import org.jetbrains.annotations.NotNull;

/**
 * No description available.
 *
 * @author mindcubr
 * @since 1.0-0.1
 */
public interface IDBFetcher<T extends User> {

    T fetchFromURL(@NotNull final String url) throws JSDBFetchingException;

    T fetchPlayerFromResponse(@NotNull DBResponse response) throws JSDBFetchingException;

}
