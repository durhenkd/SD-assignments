package persistence;

import model.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public final class PersistenceClass<T> {

    private static final EntityManagerFactory entityManagerFactory =
            javax.persistence.Persistence.createEntityManagerFactory("ro.tutorial.lab.SD");

    private static EntityManager getEntityManager(){ return entityManagerFactory.createEntityManager();}


    public static final PersistenceClass<User> userPersistenceInstance = new PersistenceClass<>(User.class);
    public static final PersistenceClass<Agency> agencyPersistenceInstance = new PersistenceClass<>(Agency.class);
    public static final PersistenceClass<Destination> destinationPersistenceInstance = new PersistenceClass<>(Destination.class);
    public static final PersistenceClass<VacationPackage> packagePersistenceInstance = new PersistenceClass<>(VacationPackage.class);

    /** class reference for Java Reflexion */
    private final Class<T> clazz;

    /** private entity manager */
    private final EntityManager entityManager = getEntityManager();

    private PersistenceClass(Class<T> clazz) {
        this.clazz = clazz;
    }

    public int getMaxId(){
        String sql = "SELECT MAX(u.id) FROM " + clazz.getSimpleName() + " u";

        Query query = this.entityManager.createQuery(sql);
        return query.getMaxResults();
    }

    public T queryById(int id){
        return /*query(List.of("id"), List.of(" = " + id)).get(0);*/ this.entityManager.find(clazz, id);
    }
    public List<T> queryByField(String fieldName, String value){
        return query(List.of(fieldName), List.of(" = " + value));
    }
    public List<T> queryAll(){
        return query(null, null);
    }

    public List<T> query(List<String> fieldNames, List<String> criterias){

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT u FROM ").append(this.clazz.getSimpleName()).append(" u"); /** strating query */

        if(fieldNames == null || criterias == null || (fieldNames.size() == 0 && criterias.size() == 0)){ /** if criteria or fieldnames are null, return all*/
            Query query = this.entityManager.createQuery(sql.toString());
            return (List<T>) query.getResultList();
        }

        if(fieldNames.size() != criterias.size()) return new ArrayList<>(); /** if lists size are different */

        sql.append(" WHERE ");

        for (int i=0; i<fieldNames.size()-1; ++i)
            sql.append("u.").append(fieldNames.get(i)).append(" ").append(criterias.get(i)).append(" and ");

        sql.append("u.").append(fieldNames.get(fieldNames.size()-1)).append(" ").append(criterias.get(criterias.size()-1));

        System.out.println(sql.toString());

        Query query = this.entityManager.createQuery(sql.toString());
        return (List<T>) query.getResultList();
    }

    public void persist(T object){
        this.entityManager.getTransaction().begin();
        this.entityManager.persist(object);
        this.entityManager.getTransaction().commit();
    }

    public void persist(List<T> objects){
        this.entityManager.getTransaction().begin();
        objects.forEach(this.entityManager::persist);
        this.entityManager.getTransaction().commit();
    }

    public void update(T object){
        this.entityManager.getTransaction().begin();
        this.entityManager.merge(object);
        this.entityManager.getTransaction().commit();
    }
    public void delete(int id){ delete(this.entityManager.find(clazz, id)); }
    public void delete(T object){
        this.entityManager.getTransaction().begin();
        /**
         * The Hibernate session (aka EntityManager's persistent context) is closed and invalidated after the commit(),
         * because it is bound to a transaction. Your object goes into a detached status.
         *
         * Indeed, if you reopen a new persistent context, your object isn't known as in a persistent state
         * in this new context, so you have to merge it. Merge sees that your person object has a primary key (id),
         * so it knows it is not new and must hit the database to reattach it.
         *
         * ote, there is NO remove method which would just take a primary key (id) and a entity class as argument.
         * You first need an object in a persistent state to be able to delete it.
         */
//        this.entityManager.merge(object);
//        this.entityManager.refresh(object);
        this.entityManager.remove(object);
        this.entityManager.flush();
        this.entityManager.getTransaction().commit();
    }

}
