package me.davidrosenstein.hci.TMC.Objects;

import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

public class Station {
  @Getter @Setter
  Location location;
  @Getter
  String name;
  @Getter @Setter
  Button connectedButton;
  HashMap<String, Boolean> options;

  public Station(String name, Location location) {
    this.name = name;
    this.location = location;
    options = new HashMap<>();
  }

  @Override
  public String toString() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
    if (connectedButton != null) {
      Text textObj = (Text) connectedButton.getGraphic();
      textObj.setText(name);
    }
  }

  public boolean getOption(String option) {
    if (options.containsKey(option)) {
      return options.get(option);
    }
    return false;
  }

  public void setOption(String option, boolean mode) {
    options.put(option, mode);
  }

  public void setLocation(double longitude, double latitude) {
    location.setLongitude(longitude);
    location.setLatitude(latitude);
  }
}
