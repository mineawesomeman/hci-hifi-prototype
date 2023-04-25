package me.davidrosenstein.hci.TMC.Objects;

import javafx.scene.shape.Polyline;
import javafx.scene.shape.StrokeLineCap;
import javafx.util.Pair;
import me.davidrosenstein.hci.TMC.Controllers.MapController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DisplayLine extends Polyline {

  List<Pair<Double, Double>> points;

  @SafeVarargs
  public DisplayLine(Pair<Double, Double> point1, Pair<Double, Double> point2, Pair<Double, Double>... points) {
    super();
    this.points = new ArrayList<>();
    this.points.add(point1);
    this.points.add(point2);
    this.points.addAll(Arrays.asList(points));

    double layX = this.points.stream().mapToDouble(Pair::getKey).average().orElse(0);
    double layY = this.points.stream().mapToDouble(Pair::getValue).average().orElse(0);

    List<Double> relatives = new ArrayList<>();

    relatives.add(this.points.get(0).getKey() - layX);
    relatives.add(this.points.get(0).getValue() - layY);

    for (int i = 1; i < this.points.size() - 1; i++) {
      double middleX = this.points.get(i).getKey();
      double middleY = this.points.get(i).getValue();
      double lastX = this.points.get(i-1).getKey();
      double lastY = this.points.get(i-1).getValue();

      double startMagnitude = Math.sqrt(Math.pow(lastX - middleX, 2) + Math.pow(lastY - middleY, 2));

    }

    this.getPoints().addAll(relatives);
    this.setLayoutX(layX);
    this.setLayoutY(layY);
    this.setStrokeLineCap(StrokeLineCap.ROUND);
    this.setStrokeWidth(10);
  }

  public void addPoint(Pair<Double, Double> point) {
    addPoint(point.getKey(), point.getValue());
  }

  public void addPoint(double x, double y) {

  }

}
