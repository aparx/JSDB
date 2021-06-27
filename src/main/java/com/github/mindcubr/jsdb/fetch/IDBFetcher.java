package com.github.mindcubr.jsdb.fetch;

import com.github.mindcubr.jsdb.deserialize.User;
import com.github.mindcubr.jsdb.fetch.http.DBResponse;
import com.github.mindcubr.jsdb.exception.JSDBFetchingException;
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
