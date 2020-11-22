package com.goodapi.web.resource;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

/**
 * @author msaritas
 */
@Getter
public class ErrorResource extends AbstractMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty(value = "error_description")
    private final String desc;

    @JsonProperty(value = "error")
    private final String error;

    @JsonProperty(value = "error_level")
    private final String errorLevel;

    public ErrorResource(String error) {
        super(MessageType.ERROR);
        this.error = error;
        this.desc = "";
        this.errorLevel = "error";
    }

    public ErrorResource(String error, String desc) {
        super(MessageType.ERROR);
        this.error = error;
        this.desc = desc;
        this.errorLevel = "error";
    }

    public ErrorResource(String error, String desc, String errorLevel) {
        super(MessageType.ERROR);
        this.error = error;
        this.desc = desc;
        this.errorLevel = errorLevel;
    }

}
