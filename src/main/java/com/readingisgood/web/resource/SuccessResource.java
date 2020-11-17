package com.readingisgood.web.resource;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.readingisgood.web.resource.AbstractMessage;
import com.readingisgood.web.resource.MessageType;

import lombok.Getter;

/**
 * @author msaritas
 */
@Getter
public class SuccessResource extends AbstractMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty(value = "success")
    private final String success;

    @JsonProperty(value = "success_description")
    private final String desc;

    public SuccessResource(String success) {
        super(MessageType.SUCCESS);
        this.success = success;
        this.desc = "";
    }

    public SuccessResource(String success, String desc) {
        super(MessageType.SUCCESS);
        this.success = success;
        this.desc = desc;
    }

}
