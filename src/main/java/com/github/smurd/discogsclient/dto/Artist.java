package com.github.smurd.discogsclient.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Get an artist.
 */
@Data
@ToString
public class Artist {
    private String name;
    private int id;
    @JsonProperty(value = "resource_url")
    private String resourceUrl;
    private String uri;
    @JsonProperty(value = "releases_url")
    private String releasesUrl;
    List<Image> images;
    private String realname;
    private String profile;
    private List<String> urls;
    private List<String> namevariations;
    List<Group> groups;
    @JsonProperty(value = "data_quality")
    private String dataQuality;
}
