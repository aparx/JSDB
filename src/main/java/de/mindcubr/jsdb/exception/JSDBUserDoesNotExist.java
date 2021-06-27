package de.mindcubr.jsdb.exception;

/**
 * Exception usually thrown during a fetch or request process
 * to indicate that a certain URL or User is not existing or invalid.
 *
 * @author mindcubr
 * @since 1.0-0.1
 */
public class JSDBUserDoesNotExist extends JSDBFetchingException {

    private final String username;

    /**
     * Exception usually thrown during a fetch or request process
     * to indicate that a certain URL or User is not existing or invalid.
     *
     * @param url      the url that failed to be fetched or loaded
     * @param username the username of the profile that does not exist
     * @author mindcubr
     * @since 1.0-0.1
     */
    public JSDBUserDoesNotExist(String url, String username) {
        super("The user '" + username + "' does not exist.", url);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

}
