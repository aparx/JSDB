package de.mindcubr.jsdb.fetch;

import de.mindcubr.jsdb.deserialize.User;
import de.mindcubr.jsdb.exception.JSDBFetchingException;
import de.mindcubr.jsdb.exception.JSDBUserDoesNotExist;
import de.mindcubr.jsdb.fetch.http.DBResponse;
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
