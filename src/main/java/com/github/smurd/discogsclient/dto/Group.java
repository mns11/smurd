package com.github.smurd.discogsclient.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Group info.
 */
@Data
public class Group {
    private int id;
    private String name;
    @JsonProperty(value = "resource_url")
    private String resourceUrl;
    private Boolean active;
}
