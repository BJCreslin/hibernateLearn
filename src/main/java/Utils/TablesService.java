package Utils;

import entities.ITEMDAO;
import entities.ItemtableEntity;

import java.util.List;

public class TablesService {

    public void itemSave(ItemtableEntity item1) {
        ItemtableEntity item=new ItemtableEntity();

        List<ItemtableEntity> itemtableEntityArrayList = ITEMDAO.getALL();
        if (ITEMDAO.isPresents(item)) ITEMDAO.update(item);
        else ITEMDAO.save(item);
    }

}
