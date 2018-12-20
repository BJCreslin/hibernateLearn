package entities;

import Utils.HibernateSessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class ITEMDAO {
    public static List<ItemtableEntity> getALL() {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        List<ItemtableEntity> list = session.createCriteria(ItemtableEntity.class).list();
        tx1.commit();
        session.close();
        return list;
    }

    public static void update(ItemtableEntity item) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(item);
        tx1.commit();
        session.close();
    }

    public static ItemtableEntity findById(int id) {
        return HibernateSessionFactory.getSessionFactory().openSession().get(ItemtableEntity.class, id);
    }


    public static void save(ItemtableEntity item) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(item);
        tx1.commit();
        session.close();
    }

    public static void delete(ItemtableEntity item) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(item);
        tx1.commit();
        session.close();
    }

    public static boolean isPresents(ItemtableEntity item) {
        List<ItemtableEntity> itemtableEntityList = getALL();
        for (ItemtableEntity itemtableEntity : itemtableEntityList) {
            if (itemtableEntity.equals(item)) return true;
        }
        return false;
    }
}
