package io.lindx.july_chat.chat_app;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.layout.VBox;

public class ChatController {

  public VBox mainChatPanel;
  public VBox loginPanel;

  public void mockAction(ActionEvent actionEvent) {

  }

  public void exit(ActionEvent actionEvent) {
    Platform.exit();
  }
}
