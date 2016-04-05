package com.pantrycar.system.representations;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pantrycar.system.representations.error.Error;
import lombok.*;

/**
 * Created by kunal.agarwal on 12/11/15.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class BasicResponse {

    protected boolean responseStatus = true;
    protected int responseCode = 200;
    protected Error error;
    protected String message;

    public <T extends BasicResponse> T error(int code, String message) {
        this.setResponseCode(code);
        this.setResponseStatus(false);
        this.setError(new Error());
        this.getError().setMessage(message);
        this.getError().setCode(code);
        return (T) this;
    }
}
