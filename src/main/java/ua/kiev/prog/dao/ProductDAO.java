package ua.kiev.prog.dao;

import ua.kiev.prog.entity.Product;

import java.util.List;

public interface ProductDAO {
    List<Product> productList();

    Product findProduct(String code);
}
