package io.lindx.jfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Controller {

  @FXML
  public Label label;
  
  @FXML
  public Button button;

  @FXML
  public void click(ActionEvent actionEvent){

    System.out.println("Button Click");
    
    label.setText("ChangeTExt");

    button.setScaleY(5.0);
    button.setScaleX(5.0);
    button.setText("PRESSED");
  }
}
