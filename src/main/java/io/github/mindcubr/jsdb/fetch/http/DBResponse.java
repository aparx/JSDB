package io.github.mindcubr.jsdb.fetch.http;

import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

/**
 * No description available.
 *
 * @author mindcubr
 * @since 1.0-0.1
 */
public class DBResponse {

    /**
     * The OkHttpResponse instance.
     */
    @NotNull
    private final Response response;

    private DBRequest causer;

    protected DBResponse(@NotNull Response response, @NotNull DBRequest causer) {
        this.response = response;
        this.causer = causer;
    }

    /**
     * Returns the main body response content as a {@link String}.
     *
     * @return the actual body content.
     * @throws IndexOutOfBoundsException - if the body content is too big
     * and a buffer overflow would occur if not thrown.
     */
    public String getContent() {
        //Get the body of the response and null-check
        ResponseBody body = response.body();
        if (body != null) {
            try {
                //Return a new string out of the response body bytes
                return new String(body.bytes());
            } catch (IOException exc) {
                //Rethrow the exception as an RuntimeException
                throw new IndexOutOfBoundsException(exc.getMessage());
            }
        }
        return StringUtils.EMPTY;
    }

    /**
     * Returns the response code.
     *
     * @see Response#code()
     * @see #getResponse()
     * @see #response
     */
    public int getCode() {
        return response.code();
    }

    /**
     * The actual origin of this response.
     * <p>The returning instance contains much more
     * details about the actual Http-{@link Response} using
     * the <em>OkHttp3-Library</em>.
     *
     * @return the original response, so the origin of this
     * extended response.
     */
    @NotNull
    public Response getResponse() {
        return response;
    }

    /**
     * The {@link DBRequest request} that caused this response.
     */
    @NotNull
    public DBRequest getCauser() {
        return causer;
    }

    /**
     * Creates a response container for the input {@code response}
     * made by the okhttp3 post request. The actual request containing
     * that third party instance is contained in the input {@code request}
     * instance.
     *
     * @param response the response received after a post {@code request}
     * @param request  the request (container) sent that caused the {@code response}
     * @return a new {@link DBResponse} linked to both inputs.
     */
    public static DBResponse of(@NotNull Response response,
                                @NotNull DBRequest request) {
        Objects.requireNonNull(response);
        Objects.requireNonNull(request);
        return new DBResponse(response, request);
    }

}
