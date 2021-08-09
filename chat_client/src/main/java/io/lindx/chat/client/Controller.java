package io.lindx.chat.client;

import java.net.URL;
import java.util.ResourceBundle;

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

  @FXML
  private VBox mainChatPanel;
  @FXML
  private TextArea mainChatArea;
  @FXML
  private TextField inputField;
  @FXML
  private Button btnSendMsg;
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

    StringBuilder msg = new StringBuilder(inputField.getText());

    if (!msg.isEmpty()) {

      mainChatArea.appendText(

          "ME:\t".concat(msg.toString()).concat("\n"));

      inputField.clear();
    }
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    
    ObservableList<String> list = FXCollections.observableArrayList("User1", "User1", "User1", "User1", "User1");
    contactList.setItems(list);

    System.out.println(contactList.getSelectionModel().getSelectedItem());
  }
}
