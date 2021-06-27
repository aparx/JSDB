package com.github.mindcubr.jsdb;

/**
 * Generic variables that can be easily configured if
 * something must be changed.
 *
 * @author mindcubr
 * @since 1.0-0.1
 */
public class Globals {

    /**
     * Pre-formatted URL to fetch a user by its username.
     * <p>There are two String-TBF placeholder:
     * <ol>
     *     <li>%s - Target platform of search</li>
     *     <li>%s - The target current username</li>
     * </ol>
     *
     * @see String#format(String, Object...)
     */
    public static final String URL_FETCH_USER = "https://api.statsdb.net/r6/%s/player/%s";

    /**
     * Pre-formatted URL to fetch a user by its user-ID.
     * <p>There is one String-TBF placeholder: %s.
     *
     * @see String#format(String, Object...)
     */
    public static final String URL_FETCH_ID = "https://api.statsdb.net/r6/player/%s";

}
