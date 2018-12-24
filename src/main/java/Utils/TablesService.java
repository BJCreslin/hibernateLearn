package Utils;

import Models.ItemTable;
import entities.ITEMDAO;
import entities.ItemtableEntity;

import java.util.List;

public class TablesService {

    public static void itemSave(ItemtableEntity item) {
        List<ItemtableEntity> itemtableEntityArrayList = ITEMDAO.getALL();
        if (ITEMDAO.isPresents(item)) ITEMDAO.delete(item);
        ITEMDAO.save(item);

    }

    public static boolean ifExist(ItemTable item1, List <ItemtableEntity> listItem) {
        boolean result = false;
        for (ItemtableEntity itemtableEntity:listItem){
            if(item1.getCode()==itemtableEntity.getCode()){
                return true;
            }
        }

        return result;
    }


}
