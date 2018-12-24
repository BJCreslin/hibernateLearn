import Models.ItemTable;
import Utils.HibernateSessionFactory;
import Utils.TablesService;
import Utils.files.readerXLS;
import entities.ItemtableEntity;
import entities.LogisttableEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class Solution {
    public static void main(String[] args) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();

        LogisttableEntity logisttableEntity = new LogisttableEntity();
        logisttableEntity.setName("Виталий");
        logisttableEntity.setSecondname("Каммер");
        logisttableEntity.setPhone(416);
        session.save(logisttableEntity);
        session.getTransaction().commit();


        List<ItemTable> tableItemFromXLS = readerXLS.getItemList();
        List<ItemTable> itemTableList = readerXLS.getBaza8List();
        List<ItemtableEntity> itemtableEntityList = new ArrayList<>();
        System.out.println("xls: " + itemTableList.size());
        System.out.println(tableItemFromXLS.size());

        Transaction tx1 = session.beginTransaction();

/**
 * Записываем те данные которые есть в таблице нужныхх остатков.
 * И если есть в наличие  на центральном и на выставке
 */
        for (ItemTable itemTable : tableItemFromXLS) {
            ItemtableEntity itemtableEntity = new ItemtableEntity();
            itemtableEntity.setCode(itemTable.getCode());
            itemtableEntity.setName(itemTable.getName());
            itemtableEntity.setNeeded(itemTable.getNeeded());
            for (ItemTable itemTableRemnants : itemTableList) {
                if (itemTable.getCode() == itemTableRemnants.getCode()) {
                    itemtableEntity.setCentral(itemTableRemnants.getCentral());
                    itemtableEntity.setVystavka(itemTableRemnants.getVystavka());
                }
            }
            session.save(itemtableEntity);
            itemtableEntityList.add(itemtableEntity);
        }

        /**
         * Записываем в данные те , которых нет в таблице нужных,
         * но есть на складах центральном или выставке
         */
        for (ItemTable itemTableremnants : itemTableList) {
            if (TablesService.ifExist(itemTableremnants, itemtableEntityList)) {
                ItemtableEntity itemtableEntity = new ItemtableEntity();
                itemtableEntity.setCode(itemTableremnants.getCode());
                itemtableEntity.setName(itemTableremnants.getName());
                itemtableEntity.setVystavka(itemTableremnants.getVystavka());
                itemtableEntity.setCentral(itemTableremnants.getCentral());
                session.save(itemtableEntity);
            }
        }
        tx1.commit();
        session.close();
    }
}
