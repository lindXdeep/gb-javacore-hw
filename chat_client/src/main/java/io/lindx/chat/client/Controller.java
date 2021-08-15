package io.lindx.chat.client;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import io.lindx.chat.client.net.Connection;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class Controller implements Initializable {

  private Connection connection;

  @FXML
  private VBox mainChatPanel;
  @FXML
  private TextArea mainChatArea;
  @FXML
  private TextField inputField;
  @FXML
  private Button btnSendMsg;
  @FXML
  private Button btnConnect;
  @FXML
  private Button btnDisconnect;

  @FXML
  private TextField serverAddres;

  @FXML
  private ListView<String> contactList;

  @FXML
  public void mockAction(ActionEvent actionEvent) {

  }

  @FXML
  public void exit(ActionEvent actionEvent) {
    Platform.exit();
  }

  @FXML
  public void sendMessage(ActionEvent actionEvent) {

    String user = contactList.getSelectionModel().getSelectedItem();

    StringBuilder msg = new StringBuilder(inputField.getText());

    if (!msg.isEmpty()) {

      mainChatArea.appendText(

          "ME".concat((user != null ? " -> " + user : "")).concat(":\t").concat(msg.toString()).concat("\n"));

      inputField.clear();
    }
  }

  @FXML
  public void connectToServer() {
    String[] address = serverAddres.getText().split(":");
    connection = new Connection(address[0], Integer.parseInt(address[1]));
    connection.call();
  }

  @FXML
  public void disconnectServer() {
    connection.send("/End");
    connection.shotdown();
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    ObservableList<String> list = FXCollections.observableArrayList("User1", "User1", "User1", "User1", "User1");
    contactList.setItems(list);

    serverAddres.setText("127.0.0.1:8181");
  }
}
