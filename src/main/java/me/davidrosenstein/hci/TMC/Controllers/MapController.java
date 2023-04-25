package me.davidrosenstein.hci.TMC.Controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;
import me.davidrosenstein.hci.TMC.Objects.DisplayLine;

import java.util.ArrayList;
import java.util.List;

public class MapController {

  @FXML private Rectangle background;
  @FXML private AnchorPane mainPane;


  private double lastX, lastY;

  private List<Node> mapObjects;

  @FXML
  public void initialize() {
    background.widthProperty().bind(mainPane.widthProperty());
    background.heightProperty().bind(mainPane.heightProperty());

    DisplayLine line = new DisplayLine(pairOf(100.0, 100.0), pairOf(125.0, 100.0), pairOf(125.0, 125.0));
    mainPane.getChildren().add(line);

    mapObjects = new ArrayList<>(mainPane.getChildren().stream().filter((n) -> n.getClass() != Rectangle.class).toList());
  }

  @FXML
  public void testbutton() {
    System.out.println(mainPane.getWidth());
  }

  public void backgroundDrag(MouseEvent event) {
    dragScene(event);

    lastX = event.getSceneX();
    lastY = event.getSceneY();
  }

  public void backgroundStartDrag(MouseEvent event) {
    System.out.println("start");

    lastX = event.getSceneX();
    lastY = event.getSceneY();

  }

  public void backgroundEndDrag(MouseEvent event) {
    System.out.println("end");

    dragScene(event);
  }

  private void dragScene(MouseEvent event) {
    double deltaX = event.getSceneX() - lastX;
    double deltaY = event.getSceneY() - lastY;

    for (int i = 0; i < mapObjects.size(); i++) {
      Node n = mapObjects.get(i);

      if (n.getLayoutX() + deltaX - (n.getLayoutBounds().getWidth()/2) < 0 ||
          n.getLayoutX() + deltaX + (n.getLayoutBounds().getWidth()/2) > mainPane.getWidth() ||
          n.getLayoutY() + deltaY + (n.getLayoutBounds().getHeight()/2) > mainPane.getHeight() ||
          n.getLayoutY() + deltaY - (n.getLayoutBounds().getHeight()/2) < 0) {
        //System.out.println("nope " + n.getClass() + " x: " + n.getLayoutX() + " y:" + n.getLayoutY());
        for (int j = i-1; j >= 0; j--) {
          Node m = mapObjects.get(j);
          m.setLayoutX(m.getLayoutX() - deltaX);
          m.setLayoutY(m.getLayoutY() - deltaY);
        }
        break;
      }
      n.setLayoutX(n.getLayoutX() + deltaX);
      n.setLayoutY(n.getLayoutY() + deltaY);
    }
  }

  public static <K, V> Pair<K, V> pairOf(K key, V value) {
    return new Pair<>(key, value);
  }

}
