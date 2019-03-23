package org.graylog.plugins.teams.client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

public class TeamsMessageCard {

  private String type = "MessageCart";
  private String context = "https://schema.org/extensions";
  private String themeColor;
  private String title;
  private String text;
  private List<Section> sections;

  public TeamsMessageCard(String color, String title, String text, String detailMsg) {
    this.themeColor = color;
    this.title = title;
    this.text = text;
    if (!StringUtils.isEmpty(detailMsg)) {
      this.sections = Lists.newArrayList(new Section("Detail Message:", detailMsg));
    }
  }

  public String toJsonString() {
    Map<String, Object> params = new HashMap<>();
    params.put("@type", type);
    params.put("@context", context);
    params.put("themeColor", themeColor);
    params.put("title", title);
    params.put("text", text);
    params.put("sections", sections);

    try {
      return new ObjectMapper().writeValueAsString(params);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Failed to build request payload as JSON format.");
    }
  }

  @JsonInclude(Include.NON_NULL)
  public static class Section {
    @JsonProperty
    public String title;
    @JsonProperty
    public String text;

    @JsonCreator
    public Section(String title, String text) {
      this.title = title;
      this.text = text;
    }
  }
}
