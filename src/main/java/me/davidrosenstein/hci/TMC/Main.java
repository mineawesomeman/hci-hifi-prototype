package me.davidrosenstein.hci.TMC;

public class Main extends Thread{

  public static void main(String[] args) {

    //System.out.println(App.class.getResource("views/Main.fxml"));

    App.launch(App.class, args);
  }
}
