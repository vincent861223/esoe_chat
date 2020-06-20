package main;

import com.jfoenix.controls.JFXDialog;
import container.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import util.CurrentUserInfo;
import util.Maps;

import java.net.URL;
import java.util.ResourceBundle;

public class ChatListSlideController implements Initializable, ListviewController {

    @FXML
    private VBox vBox;

    @FXML
    private ListView<ListCellItem> listView;
    private ObservableList<ListCellItem> obsList = FXCollections.observableArrayList();

    private JFXDialog dialog = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listView.setFocusTraversable(false);
        Platform.runLater(this::reload);
    }

    @FXML
    void clickedCreateNewChatroom(ActionEvent event) {
        VBox content = (VBox) Maps.parents.get(Maps.NEW_CHAT_DIALOG);
        StackPane root = (StackPane) Maps.parents.get(Maps.ROOT_STACK_PANE);
        ((NewChatDialogController) Maps.controllers.get(Maps.NEW_CHAT_DIALOG)).reload();
        dialog = new JFXDialog(root, content, JFXDialog.DialogTransition.TOP);
        dialog.show();
    }

    void closeDialog() { if (dialog != null) dialog.close(); }

    @Override
    public void reload() {
        obsList.clear();
        Response response = CurrentUserInfo.chatController.getChatroomList();
        String[] chatroomIDs = ((ChatroomList) response.info).chatroomIDs;
        for (String id: chatroomIDs) {
            MessageHistory msgHistory = (MessageHistory)CurrentUserInfo.chatController.getHistory(id).info;
            if (!msgHistory.messages.isEmpty())
                obsList.add(new ListCellChatroomItem(id, id, msgHistory.messages.get(msgHistory.messages.size() - 1).msg));
            else
                obsList.add(new ListCellChatroomItem(id, id, null));

            // TODO: chatroom ID & chatroom Title

        }
        listView.getItems().clear();
        listView.getItems().addAll(obsList);
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listView.setCellFactory(p -> {
            ListCell<ListCellItem> cell = new ListViewCell();
//            cell.setOnMousePressed(e -> {
//                if (e.getClickCount() == 2){
//                    // TODO: the right slide changes to this chat
//                    System.out.println(cell.getItem() + " double clicked!");
////                     Maps.displayChatroom();
//                }
//            });
            return cell;
        });
    }
}
