package me.davidrosenstein.hci.TMC.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MainController {

  @FXML Button generateMapButton;
  @FXML ListView<Node> stationsList;
  ObservableList<Node> listViewItems;

  @FXML
  public void initialize() {
    listViewItems = FXCollections.observableArrayList();
    stationsList.setItems(listViewItems);
  }

  @FXML
  public void addStation() {
    listViewItems.add(new Button("yahoo"));
  }

  @FXML
  public void generateMap() {
    log.debug("yippee");
  }

}
