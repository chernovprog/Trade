package ua.kiev.prog.dao;

import ua.kiev.prog.model.CartInfo;

public interface OrderDAO {
    void saveOrder(CartInfo cartInfo);

    void updateOrder(CartInfo cartInfo);
}
