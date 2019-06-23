package ua.kiev.prog.dao;

import org.springframework.stereotype.Repository;
import ua.kiev.prog.entity.Product;
import ua.kiev.prog.entity.Store;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Repository
public class StoreDAOImpl implements StoreDAO {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Store findStoreByProductId(Long productId) {
        TypedQuery<Store> query;
        Store store = null;
        try {
            query = em.createQuery("SELECT s FROM Store AS s WHERE s.product.id = :productId", Store.class);
            query.setParameter("productId", productId);
            store = query.getSingleResult();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return store;
    }

    @Override
    public void updateStore(Product product, int oldQuantity, int newQuantity) {
        long productId = product.getId();
        Store store = findStoreByProductId(productId);
        int quantityDiff = newQuantity - oldQuantity;
        store.setQuantity(store.getQuantity() - quantityDiff);
        em.persist(store);
    }
}
