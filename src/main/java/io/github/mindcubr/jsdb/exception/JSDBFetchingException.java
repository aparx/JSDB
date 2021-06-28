package io.github.mindcubr.jsdb.exception;

/**
 * Exception usually thrown when a post request or fetching
 * process went wrong. Can be seen as a more detailed version
 * of {@link JSDBUserDoesNotExist}.
 *
 * @author mindcubr
 * @see JSDBUserDoesNotExist
 * @since 1.0-0.1
 */
public class JSDBFetchingException extends Exception {

    private final String url;

    public JSDBFetchingException(String url) {
        super("Fetching or Requesting on URL '" + url + "' threw an error.");
        this.url = url;
    }

    public JSDBFetchingException(Throwable cause, String url) {
        super(cause);
        this.url = url;
    }

    public JSDBFetchingException(String message, String url) {
        super(message);
        this.url = url;
    }

    public String getURL() {
        return url;
    }

    /**
     * Returns whether this {@link #getCause() cause} class is actually
     * a potential cause and assignable to the input {@code compare}.
     *
     * @param compare the comparable cause
     * @return whether this cause is assignable to the input {@code compare}.
     */
    public boolean isCause(Class<? extends Throwable> compare) {
        Throwable cause = this.getCause();
        if (cause == null)
            return false;
        return cause.getClass()
                .isAssignableFrom(compare);
    }

    /**
     * Returns whether this exception is actually the cause
     * out of an {@link JSDBUserDoesNotExist} exception throw.
     * <p>The following code describes what is happening
     * within this code, so the following is quite equivalent,
     * but contains no safety and unit tests:
     * <p>
     *     <pre><code>
     *         if ({@link #getCause()} instanceof {@link JSDBUserDoesNotExist}) {
     *             return true;
     *         }
     *     </code></pre>
     *
     * @since 1.0-0.1
     */
    public boolean isUserNotExisting() {
        return isCause(JSDBUserDoesNotExist.class);
    }

}
