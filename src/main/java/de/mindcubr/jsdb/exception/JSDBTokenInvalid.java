package de.mindcubr.jsdb.exception;

/**
 * Exception thrown whenever a fetching or post request
 * is failed or denied due to an issue with the concurrent
 * used {@link de.mindcubr.jsdb.bridge.DBToken token}.
 *
 * @author mindcubr
 * @see de.mindcubr.jsdb.bridge.DBToken
 * @since 1.0-0.1
 */
public class JSDBTokenInvalid extends Exception {

    private final String token;

    public JSDBTokenInvalid(String token) {
        super("The token used is incorrect and therefore invalid.");
        this.token = token;
    }

    public JSDBTokenInvalid(String message, Throwable cause, String token) {
        super(message, cause);
        this.token = token;
    }

    public String getToken() {
        return token;
    }

}
