package me.davidrosenstein.hci.TMC.Objects;

import lombok.Getter;
import lombok.Setter;

public class Location {
  @Getter @Setter
  double longitude;
  @Getter @Setter
  double latitude;

  public Location(double longitude, double latitude) {
    this.longitude = longitude;
    this.latitude = latitude;
  }

  public String getLongName() {
    return String.valueOf(longitude);
  }

  public String getLatName() {
    return String.valueOf(latitude);
  }
}
