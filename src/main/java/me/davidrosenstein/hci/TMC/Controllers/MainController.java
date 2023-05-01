package me.davidrosenstein.hci.TMC.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import me.davidrosenstein.hci.TMC.App;
import me.davidrosenstein.hci.TMC.Objects.Line;
import me.davidrosenstein.hci.TMC.Objects.Location;
import me.davidrosenstein.hci.TMC.Objects.Station;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MainController {

  @FXML private Button generateMapButton;
  @FXML private ListView<Node> stationsList;
  @FXML private ListView<Node> linesList;
  @FXML private BorderPane mainPane;
  private ObservableList<Node> stationsListViewItems;
  private ObservableList<Node> linesListViewItems;
  private List<Station> stations;
  private List<Line> lines;

  private int currentColor = 0;
  private final Color[] DEFAULT_COLORS = {
      Color.color(189.0/255, 93.0/255, 22.0/255),
      Color.color(1.0, 56.0/255, 24.0/255),
      Color.color(1.0, 206.0/255, 54.0/255),
      Color.color(9.0/255, 132.0/255, 59.0/255),
      Color.color(254.0/255, 133.0/255, 168.0/255),
      Color.color(156.0/255, 163.0/255, 169.0/255),
      Color.color(157.0/255, 23.0/255, 98.0/255),
      Color.color(44.0/255, 46.0/255, 53.0/255),
      Color.color(10.0/255, 55.0/255, 151.0/255),
      Color.color(17.0/255, 167.0/255, 224.0/255)
  };

  @FXML
  public void initialize() {
    stationsListViewItems = FXCollections.observableArrayList();
    stationsList.setItems(stationsListViewItems);

    linesListViewItems = FXCollections.observableArrayList();
    linesList.setItems(linesListViewItems);

    stations = new ArrayList<>();
    lines = new ArrayList<>();
  }

  @FXML
  public void addStation() {
    Station newStation = new Station("", new Location(0, 0));

    Text stationText = new Text(newStation.getName());
    stationText.setFont(Font.font(24));

    Button stationDisp = new Button("", stationText);
    stationDisp.setOnAction((event) -> editStation(newStation));
    newStation.setConnectedButton(stationDisp);

    stations.add(newStation);

    stationDialogBox(newStation).ifPresent((num) -> {
      if (num == 2)
        deleteStation(newStation);
      if (num == 0)
        stationsListViewItems.add(stationDisp);
    });
  }

  @FXML
  public void addLine() {
    Line newLine = new Line("New Line", DEFAULT_COLORS[currentColor]);

    Text lineText = new Text(newLine.getName());
    lineText.setFont(Font.font(24));
    Circle colorDisp = new Circle(10, newLine.getColor());
    colorDisp.setStroke(Color.BLACK);
    colorDisp.setStrokeWidth(2);

    HBox buttonContent = new HBox(lineText, colorDisp);
    buttonContent.setSpacing(5);
    buttonContent.setAlignment(Pos.CENTER_LEFT);

    Button lineDisp = new Button("", buttonContent);
    lineDisp.setOnAction((event) -> editLine(newLine));
    newLine.setConnectedButton(lineDisp);
    newLine.setConnectedText(lineText);
    newLine.setConnectedCircle(colorDisp);

    lines.add(newLine);

    lineDialogBox(newLine).ifPresent((num) -> {
      if (num == 2)
        deleteLine(newLine);
      if (num == 0) {
        linesListViewItems.add(lineDisp);

        currentColor++;
        if (currentColor >= DEFAULT_COLORS.length) {
          currentColor = 0;
        }
      }
    });
  }

  @FXML
  public void generateMap() {
    Dialog<ButtonType> dialog = new Dialog<>();
    DialogPane pane = dialog.getDialogPane();

    pane.getButtonTypes().add(ButtonType.OK);
    pane.setContentText("This feature is not working in this prototype.");

    dialog.showAndWait();

//    final FXMLLoader loader = new FXMLLoader(App.class.getResource("views/GenMap.fxml"));
//    try {
//      mainPane.setCenter(loader.load());
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
  }

  @FXML
  public void importData() {
    Dialog<ButtonType> dialog = new Dialog<>();
    DialogPane pane = dialog.getDialogPane();

    pane.getButtonTypes().add(ButtonType.OK);
    pane.setContentText("This feature is not working in this prototype");

    dialog.showAndWait();
  }

  public void editStation(Station station) {
    stationDialogBox(station);
  }

  public void editLine(Line line) {
    lineDialogBox(line);
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

    String[] checkboxes = {"Parking", "Bike Access", "Accessible"};

    for (String checkbox: checkboxes) {
      CheckBox checkBox = new CheckBox(checkbox);
      checkBox.setSelected(station.getOption(checkbox));
      optionsView.getItems().add(checkBox);
    }

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

  public Optional<Integer> lineDialogBox(Line line) {
    Dialog<Integer> dialog = new Dialog<>();
    DialogPane pane = dialog.getDialogPane();

    pane.setPrefWidth(500);
    pane.setPrefHeight(350);
    dialog.setTitle("Edit Line");

    ButtonType confirmButton = new ButtonType("Confirm", ButtonBar.ButtonData.FINISH);
    ButtonType deleteButton = new ButtonType("Delete", ButtonBar.ButtonData.NO);
    ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
    pane.getButtonTypes().addAll(confirmButton, deleteButton, cancelButton);

    Text nameText = new Text("Line Name");
    TextField nameField = new TextField(line.getName());

    Text colorText = new Text("Line Color:");
    ColorPicker picker = new ColorPicker(line.getColor());

    HBox nameBox = new HBox(nameText, nameField, colorText, picker);
    nameBox.setSpacing(10);
    nameBox.setAlignment(Pos.CENTER_LEFT);

    ObservableList<Station> workingIStations = FXCollections.observableArrayList(line.getStations());
    ObservableList<Station> workingNStations = FXCollections.observableArrayList(stations);
    workingNStations.removeAll(line.getStations());

    TextField iSearcher = new TextField();
    iSearcher.setPromptText("Not Working Yet");
    ListView<Station> iStations = new ListView<>(workingIStations);
    VBox includedStations = new VBox(iSearcher, iStations);
    includedStations.setSpacing(5);

    TextField nSearcher = new TextField();
    nSearcher.setPromptText("Not Working Yet");
    ListView<Station> nStations = new ListView<>(workingNStations);
    VBox notIncludedStations = new VBox(nSearcher, nStations);
    notIncludedStations.setSpacing(5);

    Button left = new Button("< Add");
    left.setDisable(true);
    left.disableProperty().bind(nStations.getSelectionModel().selectedItemProperty().isNull());
    left.setOnAction((action) -> {
      Station selected = nStations.getSelectionModel().getSelectedItem();
      System.out.println(selected);
      workingNStations.remove(selected);
      workingIStations.add(selected);
      nStations.getSelectionModel().clearSelection();
    });
    left.setPrefWidth(150);

    Button right = new Button("Remove >");
    right.setDisable(true);
    right.disableProperty().bind(iStations.getSelectionModel().selectedItemProperty().isNull());
    right.setOnAction((action) -> {
      Station selected = iStations.getSelectionModel().getSelectedItem();
      workingIStations.remove(selected);
      workingNStations.add(selected);
      iStations.getSelectionModel().clearSelection();
    });
    right.setPrefWidth(150);

    Button up = new Button("Move Up /\\");
    up.setDisable(true);
    up.disableProperty().bind(iStations.getSelectionModel().selectedItemProperty().isNull());
    up.setOnAction((action) -> {
      Station selected = iStations.getSelectionModel().getSelectedItem();
      int loc = iStations.getSelectionModel().getSelectedIndex();
      loc--;
      if (loc < 0) loc = 0;
      workingIStations.remove(selected);
      workingIStations.add(loc, selected);
      iStations.getSelectionModel().select(selected);
    });
    up.setPrefWidth(150);

    Button down = new Button("Move Down \\/");
    down.setDisable(true);
    down.disableProperty().bind(iStations.getSelectionModel().selectedItemProperty().isNull());
    down.setOnAction((action) -> {
      Station selected = iStations.getSelectionModel().getSelectedItem();
      int loc = iStations.getSelectionModel().getSelectedIndex();
      loc++;
      workingIStations.remove(selected);
      if (loc >= workingIStations.size()) {
        workingIStations.add(selected);
      } else {
        workingIStations.add(loc, selected);
      }
      iStations.getSelectionModel().select(selected);
    });
    down.setPrefWidth(150);

    VBox switchListBox = new VBox(left, right, up, down);
    switchListBox.setSpacing(5);
    switchListBox.setPrefWidth(200);
    switchListBox.setAlignment(Pos.CENTER);

    HBox selectionsBox = new HBox(includedStations, switchListBox, notIncludedStations);
    selectionsBox.setSpacing(5);
    selectionsBox.setAlignment(Pos.CENTER);

    VBox content = new VBox(nameBox, selectionsBox);
    content.setSpacing(10);

    pane.setContent(content);

    dialog.setResultConverter((button) -> {
      if (button.equals(confirmButton)) {
        line.setName(nameField.getText());
        line.setColor(picker.getValue());
        line.resetStations();
        line.addManyStations(workingIStations);
        return 0;
      } else if (button.equals(deleteButton)) {
        deleteLine(line);
        return 1;
      }
      return 2;
    });

    return dialog.showAndWait();
  }

  public void deleteStation(Station station) {
    stations.remove(station);
    stationsListViewItems.remove(station.getConnectedButton());
  }

  public void deleteLine(Line line) {
    lines.remove(line);
    linesListViewItems.remove(line.getConnectedButton());
  }

}
