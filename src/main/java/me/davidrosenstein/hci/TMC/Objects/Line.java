package me.davidrosenstein.hci.TMC.Objects;

import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.function.Consumer;

public class Line implements Iterable<Station>{

  @Getter @Setter
  private List<Station> stations;

  public void setName(String name) {
    this.name = name;
    if (connectedText != null) {
      connectedText.setText(name);
    }
  }

  public void setColor(Color color) {
    this.color = color;
    if (connectedCircle != null) {
      connectedCircle.setFill(color);
    }
  }

  @Getter
  private String name;

  @Getter
  private Color color;

  @Getter @Setter
  private Button connectedButton;

  @Getter @Setter
  private Text connectedText;

  @Getter @Setter
  private Circle connectedCircle;

  public Line(String name, Color color) {
    stations = new ArrayList<>();
    this.name = name;
    this.color = color;
  }

  public void addStation(Station station) {
    stations.add(station);
  }

  public void addManyStations(Collection<Station> stations) {
    this.stations.addAll(stations);
  }

  public void addManyStations(Station... stations) {
    this.stations.addAll(List.of(stations));
  }

  public void removeStation(Station station) {
    stations.remove(station);
  }

  public void resetStations() {
    stations = new ArrayList<>();
  }

  public Line copyOf() {
    Line copy = new Line(name, color);
    copy.stations = List.copyOf(stations);
    return copy;
  }

  @Override
  public Iterator<Station> iterator() {
    return stations.iterator();
  }

  @Override
  public void forEach(Consumer<? super Station> action) {
    stations.forEach(action);
  }

  @Override
  public Spliterator<Station> spliterator() {
    return stations.spliterator();
  }
}
