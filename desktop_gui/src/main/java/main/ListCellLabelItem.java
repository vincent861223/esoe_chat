package main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class ListCellLabelItem extends ListCellItem {

    @FXML
    private Label label2;

    public ListCellLabelItem() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("listCellLabelItem.fxml"));
        fxmlLoader.setController(this);
        try {
            hBox = fxmlLoader.load();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ListCellLabelItem(String labelText) {
        this();
        label1.setText(labelText);
        label2.setText(lorem);
    }

    public ListCellLabelItem(String str1, String str2) {
        this();
        label1.setText(str1);
        label2.setText(str2);
    }

}