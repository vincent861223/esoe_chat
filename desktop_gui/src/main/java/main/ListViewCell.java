package main;

import javafx.scene.control.ListCell;

public class ListViewCell extends ListCell<ListCellItem>
{
    @Override
    public void updateItem(ListCellItem item, boolean empty)
    {
        super.updateItem(item,empty);
        if(item != null) {
            setGraphic(item.getBox());
        }
        else {
            setGraphic(null);
        }
    }
}