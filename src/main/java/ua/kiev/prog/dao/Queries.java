package ua.kiev.prog.dao;

public interface Queries {
    String GET_PRODUCTS = "FROM Product";
    String GET_PRODUCTS_BY_CODE = "SELECT p FROM Product AS p WHERE p.code = :code";
    String GET_MAX_ORDER_NUM = "SELECT max(o.orderNum) FROM Order o";
    String GET_ORDER_BY_ORDERNUM = "SELECT o FROM Order AS o WHERE o.orderNum = :orderNum";
    String GET_ORDERDETAIL_BY_ORDERID_AND_PRODUCTID = "SELECT od FROM OrderDetail AS od " +
            "WHERE od.order.id = :ORDER_ID AND od.product.id = :PRODUCT_ID";
}
