package com.diplomaproject.tendersystembackend.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HttpResponse {
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "MM-dd-yyy hh:mm:ss",
            timezone = "UTC"
    )
    private Date timestamp;
    private int code;
    private HttpStatus status;
    private String reason;
    private String message;

    public HttpResponse(int code, HttpStatus status, String reason, String message) {
        this.timestamp = new Date();
        this.code = code;
        this.status = status;
        this.reason = reason;
        this.message = message;
    }
}
