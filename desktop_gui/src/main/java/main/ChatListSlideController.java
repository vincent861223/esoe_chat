package main;

import com.jfoenix.controls.JFXDialog;
import container.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import util.ChatInfo;
import util.CurrentUser;
import util.Maps;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class ChatListSlideController implements Initializable, ListviewController {

    @FXML
    private VBox vBox;

    @FXML
    private ListView<ListCellItem> listView;
    private ObservableList<ListCellItem> obsList = FXCollections.observableArrayList();
    private final Set<String> removeSet = new HashSet<>();
    private final Set<String> currentSet = new HashSet<>();

    private JFXDialog dialog = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listView.setFocusTraversable(false);
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listView.setItems(obsList);
        listView.setCellFactory(p -> new ListViewCell());
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
        Response response = CurrentUser.chatController.getChatroomList();
        String[] chatroomIDs = ((ChatroomList) response.info).chatroomIDs;
        for (String id: chatroomIDs) {
            currentSet.add(id);
            if (!removeSet.contains((id))) {
                MessageHistory msgHistory = (MessageHistory) CurrentUser.chatController.getHistory(id).info;
                ListCellChatroomItem newItem;
                String chatroomName = ChatInfo.getChatroomName(id);
                // Display Last message
                if (!msgHistory.messages.isEmpty())
                    newItem = new ListCellChatroomItem(chatroomName, id, msgHistory.messages.get(msgHistory.messages.size() - 1).msg);
                else
                    newItem = new ListCellChatroomItem(chatroomName, id, null);
                Maps.chatroomListItems.put(id, newItem);
                obsList.add(newItem);
            }
        }
            removeSet.removeAll(currentSet);
            for (String roomID: removeSet) {
                obsList.removeIf(item -> roomID.equals(((ListCellChatroomItem) item).getChatroomID()));
            }
            removeSet.clear();
            removeSet.addAll(currentSet);
            currentSet.clear();
            // TODO: chatroom ID & chatroom Title

    }
}
