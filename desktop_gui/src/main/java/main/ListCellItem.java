package main;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

// Custom Control for list view cell item
public abstract class ListCellItem {

    @FXML
    protected HBox hBox;
    @FXML
    protected Label label;

    public String getLabelText() {
        return label.getText();
    }
    public HBox getBox()
    {
        return hBox;
    }

}