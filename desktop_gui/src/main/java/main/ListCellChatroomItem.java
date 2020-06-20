package main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import util.Maps;

import java.io.IOException;

public class ListCellChatroomItem extends ListCellItem {

    @FXML
    private Label label2;

    private String chatroomID;

    public ListCellChatroomItem() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("listCellChatroomItem.fxml"));
        fxmlLoader.setController(this);
        try {
            hBox = fxmlLoader.load();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ListCellChatroomItem(String chatroomTitle, String chatroomID, String lastMessage) {
        this();
        this.chatroomID = chatroomID;
        label.setText(chatroomTitle);
        label2.setText(lastMessage);
    }

    @FXML
    void mouseClicked(MouseEvent e) throws IOException {
        if (e.getClickCount() == 2){
            Maps.displayChatroom(chatroomID);
        }
    }
}