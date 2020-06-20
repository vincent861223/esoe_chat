package main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.io.IOException;

public abstract class ListCellItem
{
    @FXML
    protected HBox hBox;
    @FXML
    protected Label label1;

    protected static String lorem = "Lorem ipsum dolor sit amet";

    public ListCellItem() {
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("listCellItem.fxml"));
//        fxmlLoader.setController(this);
//        try {
//            hBox = fxmlLoader.load();
//        }
//        catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

    public String getLabel1Text() { return label1.getText(); }
    public HBox getBox()
    {
        return hBox;
    }
}