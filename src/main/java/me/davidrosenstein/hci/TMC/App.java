package me.davidrosenstein.hci.TMC;

import javafx.application.Application;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

@Slf4j
public class App extends Application {

  @Setter @Getter private static Stage primaryStage;
  @Setter @Getter private static BorderPane rootPane;

  @Override
  public void init() {
    log.info("Starting Up");
  }

  @Override
  public void start(Stage primaryStage) throws IOException {
    /* primaryStage is generally only used if one of your components require the stage to display */
    App.primaryStage = primaryStage;
    App.primaryStage.setMinHeight(700.0);
    App.primaryStage.setMinWidth(950.0);
    App.primaryStage.setTitle("Transit Map Creator");

    final FXMLLoader loader = new FXMLLoader(App.class.getResource("views/Main.fxml"));
    final BorderPane root = loader.load();

    App.rootPane = root;

    final Scene scene = new Scene(root);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  @Override
  public void stop() {
    log.info("Shutting Down");
  }
}
