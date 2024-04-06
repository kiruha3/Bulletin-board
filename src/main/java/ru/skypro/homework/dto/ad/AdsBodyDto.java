package ru.skypro.homework.dto.ad;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import org.springframework.core.io.Resource;

@Data
public class AdsBodyDto {
  /**
   * AdsBody
   */
  @JsonProperty("properties")
  private CreateOrUpdateAdDto properties = null;

  @JsonProperty("image")
  private Resource image = null;

  public AdsBodyDto properties(CreateOrUpdateAdDto properties) {
    this.properties = properties;
    return this;
  }

  public AdsBodyDto image(Resource image) {
    this.image = image;
    return this;
  }

}
