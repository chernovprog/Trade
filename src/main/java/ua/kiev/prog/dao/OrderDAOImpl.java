package ua.kiev.prog.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.kiev.prog.entity.Order;
import ua.kiev.prog.entity.OrderDetail;
import ua.kiev.prog.entity.Product;
import ua.kiev.prog.model.CartInfo;
import ua.kiev.prog.model.CartLineInfo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class OrderDAOImpl implements OrderDAO {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private StoreDAO storeDAO;

    @Override
    @Transactional
    public void saveOrder(CartInfo cartInfo) {
        int orderNum = this.getMaxOrderNum() + 1;

        Order order = new Order();
        order.setOrderNum(orderNum);
        order.setDateCreated(LocalDateTime.now());
        order.setOrderedAmount(cartInfo.getAmountTotal());
        em.persist(order);

        List<CartLineInfo> lines = cartInfo.getCartLines();

        for (CartLineInfo line : lines) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setAmount(line.getAmount());
            orderDetail.setPrice(line.getProductInfo().getPrice());
            orderDetail.setQuantity(line.getQuantity());

            String code = line.getProductInfo().getCode();
            Product product = productDAO.findProduct(code);
            orderDetail.setProduct(product);

            em.persist(orderDetail);

            storeDAO.updateStore(product, 0, line.getQuantity());
        }

        cartInfo.setOrderNum(orderNum);
    }

    @Override
    @Transactional
    public void updateOrder(CartInfo cartInfo) {
        int orderNum = cartInfo.getOrderNum();

        TypedQuery<Order> query = em.createQuery(Queries.GET_ORDER_BY_ORDERNUM, Order.class);
        query.setParameter("orderNum", orderNum);
        Order order = query.getSingleResult();
        order.setOrderedAmount(cartInfo.getAmountTotal());
        em.persist(order);

        long orderId = order.getId();
        List<CartLineInfo> lines = cartInfo.getCartLines();
        for (CartLineInfo line : lines) {
            String code = line.getProductInfo().getCode();
            Product product = productDAO.findProduct(code);
            Long productId = product.getId();

            TypedQuery<OrderDetail> orderDetailTypedQuery =
                    em.createQuery(Queries.GET_ORDERDETAIL_BY_ORDERID_AND_PRODUCTID, OrderDetail.class);
            orderDetailTypedQuery.setParameter("ORDER_ID", orderId);
            orderDetailTypedQuery.setParameter("PRODUCT_ID", productId);

            OrderDetail orderDetail = orderDetailTypedQuery.getSingleResult();
            int oldQuantity = orderDetail.getQuantity();
            orderDetail.setAmount(line.getAmount());
            int newQuantity = line.getQuantity();
            orderDetail.setQuantity(newQuantity);

            em.persist(orderDetail);

            storeDAO.updateStore(product, oldQuantity, newQuantity);
        }
    }

    private int getMaxOrderNum() {
        int value = 0;
        TypedQuery<Integer> query = em.createQuery(Queries.GET_MAX_ORDER_NUM, Integer.class);
        try {
            value = query.getSingleResult();
        } catch (NullPointerException ex) {
            System.out.println(ex);
        }

        return value;
    }
}
