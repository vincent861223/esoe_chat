package main;

import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class ListCellFriendItem extends ListCellItem {

    public ListCellFriendItem() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("listCellFriendItem.fxml"));
        fxmlLoader.setController(this);
        try {
            hBox = fxmlLoader.load();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ListCellFriendItem(String labelText) {
        this();
        label.setText(labelText);
    }
}