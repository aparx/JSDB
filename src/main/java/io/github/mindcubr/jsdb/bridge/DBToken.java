package io.github.mindcubr.jsdb.bridge;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.NotNull;

import java.util.Base64;
import java.util.Objects;

/**
 * The token class contains the final token used to authenticate
 * this application and further post requests.
 * <p>The <em>User-ID</em> and <em>Password</em> can be found when the
 * application is registered within<br>
 * <code>https://developers.statsdb.net/apps/<b>{YourApplicationID}</b>/authentication</code>
 * <p>The Developer-API from <em>statsdb.net</em> is very strict and requires that
 * authentication to work properly.
 * <p><p><b>Security Warning:</b>
 * Because the token could be reviewed through decompilers
 * or could be seen within the memory as a saved value be careful with setting
 * this token up. The token must be obfuscated in a way, that simple decompilers
 * or assembler browser cannot easily detect the token to use it their own way.
 *
 * @author mindcubr
 * @since 1.0-0.1
 */
public class DBToken {

    /**
     * Every string ending of an Base64 encoding.
     */
    private static final String BASE64_ENDING = "==";

    @NotNull
    private final String token;

    private DBToken(@NotNull String token) {
        this.token = Objects.requireNonNull(token);
    }

    /**
     * The actual immutable token saved in this class.
     *
     * @return the token saved in this class.
     */
    public String getToken() {
        return token;
    }

    /**
     * Returns a new {@link DBToken} instance with the already
     * base64-{@code cipher} <em>token</em>.
     *
     * @param cipher the cipher token that is already cipher
     *                  in a Base64 format.
     * @return a new {@link DBToken} instance of that input.
     */
    public static DBToken of(String cipher) {
        return new DBToken(cipher);
    }

    /**
     * Returns a new {@link DBToken} instance with a token
     * based of the yet unencrypted {@linkplain Base64} input;
     * <p>{@code username}:{@code password}
     * <p>That is automatically encrypted by invoking this method.
     *
     * @param username the username of the StatsDB application
     * @param password the password of the StatsDB application
     * @return a new {@link DBToken} instance of an encrypted
     * {@code username} and {@code password} caused by {@link #of(String)}
     * @see #of(String)
     */
    public static DBToken encrypt(String username, String password) {
        return of(Base64.getEncoder()
                .encodeToString((username + ':' + password).getBytes()));
    }

    /**
     * Validates that this token is correct.
     *
     * @throws IllegalArgumentException - if this {@link #token} is not
     * already encoded, undefined, empty or blank in general.
     * @see Validate#isTrue(boolean)
     * @see #BASE64_ENDING
     */
    public final void validate() {
        Validate.isTrue(StringUtils.isNotBlank(token)
                && token.endsWith(BASE64_ENDING));
    }

    /*
     * TODO: FIND A WAY TO IMPROVE SAFETY FROM ASSEMBLER BROWSER
     *   AND DECOMPILER. RUNTIME? ELSE ADD (ca.) "ofUsernameAndPassword"
     *   HERE
     */

}
