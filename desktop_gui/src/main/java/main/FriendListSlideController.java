package main;

import client.ChatController;
import com.jfoenix.controls.JFXListView;
import container.Friend;
import container.FriendList;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import util.CurrentUserInfo;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FriendListSlideController implements Initializable {

    @FXML
    private VBox vBox;
    private ListView<String> listView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("friend list initialized");
        Platform.runLater(() -> {
            CurrentUserInfo.response = CurrentUserInfo.chatController.getFriend();
//            System.out.println(CurrentUserInfo.response);
            FriendList friendList = (FriendList) CurrentUserInfo.response.info;
            List<String> stringList = new ArrayList<>();
            for(Friend friend: friendList.friends){
                if (friend.getBlocked() == false && friend.getPending() == false)
                    stringList.add(friend.getFriendUsername());
            }
            ObservableList observableList = FXCollections.observableArrayList();
            observableList.setAll(stringList);
            listView = new ListView<>();
            listView.getItems().addAll(stringList);
            listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            listView.setCellFactory(list -> new ListViewCell());
            vBox.getChildren().addAll(listView);
        });

    }
}
