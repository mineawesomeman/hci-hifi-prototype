package me.davidrosenstein.hci.TMC.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import lombok.extern.slf4j.Slf4j;
import me.davidrosenstein.hci.TMC.Objects.Location;
import me.davidrosenstein.hci.TMC.Objects.Station;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class MainController {

  @FXML Button generateMapButton;
  @FXML ListView<Node> stationsList;
  ObservableList<Node> listViewItems;
  List<Station> stations;

  @FXML
  public void initialize() {
    listViewItems = FXCollections.observableArrayList();
    stationsList.setItems(listViewItems);

    stations = new ArrayList<>();
  }

  @FXML
  public void addStation() {
    Station newStation = new Station("", new Location(0, 0));

    Text stationText = new Text(newStation.getName());
    stationText.setFont(Font.font(24));

    Button stationDisp = new Button("", stationText);
    stationDisp.setOnAction((event) -> editStation(newStation));
    newStation.setConnectedButton(stationDisp);

    stationDialogBox(newStation).ifPresent((num) -> {
      if (num == 2)
        deleteStation(newStation);
      if (num == 0)
        listViewItems.add(stationDisp);
    });
  }

  @FXML
  public void generateMap() {
    log.debug("yippee");
  }

  public void editStation(Station station) {
    stationDialogBox(station);
  }

  public Optional<Integer> stationDialogBox(Station station) {
    Dialog<Integer> dialog = new Dialog<>();
    DialogPane pane = dialog.getDialogPane();

    pane.setPrefWidth(400);
    pane.setPrefHeight(250);
    dialog.setTitle("Edit Station");

    ButtonType confirmButton = new ButtonType("Confirm", ButtonBar.ButtonData.FINISH);
    ButtonType deleteButton = new ButtonType("Delete", ButtonBar.ButtonData.NO);
    ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
    pane.getButtonTypes().addAll(confirmButton, deleteButton,  cancelButton);

    Text nameText = new Text("Station Name");
    TextField nameField = new TextField(station.getName());
    HBox nameBox = new HBox(nameText, nameField);
    nameBox.setSpacing(10);

    Text longText = new Text("Longitude");
    TextField longField = new TextField(station.getLocation().getLongName());
    Text latText = new Text("Latitude");
    TextField latField = new TextField(station.getLocation().getLatName());
    HBox locationBox = new HBox(longText, longField, latText, latField);
    locationBox.setSpacing(10);

    Text optionsText = new Text("Options");
    ObservableList<CheckBox> optionsList = FXCollections.observableArrayList();
    ListView<CheckBox> optionsView = new ListView<>(optionsList);
    HBox optionsBox = new HBox(optionsView);
    optionsView.setPrefWidth(150);

    optionsList.add(new CheckBox("Parking"));

    VBox content = new VBox(nameBox, locationBox, optionsText, optionsBox);
    pane.setContent(content);
    content.setSpacing(10);

    dialog.setResultConverter((button) -> {
      if (button.equals(confirmButton)) {
        station.setName(nameField.getText());
        station.setLocation(Double.parseDouble(longField.getText()), Double.parseDouble(latField.getText()));
        for (CheckBox option: optionsList) {
          station.setOption(option.getText(), option.isSelected());
        }
        return 0;
      } else if (button.equals(deleteButton)) {
        deleteStation(station);
        return 1;
      }
      return 2;
    });

    return dialog.showAndWait();
  }

  public void deleteStation(Station station) {
    stations.remove(station);
    listViewItems.remove(station.getConnectedButton());
  }

}
