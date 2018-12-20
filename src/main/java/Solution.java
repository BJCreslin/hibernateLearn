import Utils.HibernateSessionFactory;
import entities.LogisttableEntity;
import org.hibernate.Session;

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





        session.close();
    }
}
