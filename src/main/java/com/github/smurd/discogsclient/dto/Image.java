package com.github.smurd.discogsclient.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Image info.
 */
@Data
public class Image {
    private String type;
    private String uri;
    @JsonProperty(value = "resource_url")
    private String resourceUrl;
    private String uri150;
    private int width;
    private int height;
}
