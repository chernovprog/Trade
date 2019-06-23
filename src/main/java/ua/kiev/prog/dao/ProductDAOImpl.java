package ua.kiev.prog.dao;

import org.springframework.stereotype.Repository;
import ua.kiev.prog.entity.Product;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductDAOImpl implements ProductDAO {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Product> productList() {
        TypedQuery<Product> query;
        List<Product> products = new ArrayList<>();
        try {
            query = em.createQuery(Queries.GET_PRODUCTS, Product.class);
            products = query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return products;
    }

    @Override
    public Product findProduct(String code) {
        TypedQuery<Product> query;
        Product product = null;
        try {
            query = em.createQuery(Queries.GET_PRODUCTS_BY_CODE, Product.class);
            query.setParameter("code", code);
            product = query.getSingleResult();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return product;
    }
}
