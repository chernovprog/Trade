package ua.kiev.prog.dao;

import ua.kiev.prog.entity.Product;
import ua.kiev.prog.entity.Store;

public interface StoreDAO {
    Store findStoreByProductId(Long id);

    void updateStore(Product product, int oldQuantity, int newQuantity);
}
