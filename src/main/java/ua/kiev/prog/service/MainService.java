package ua.kiev.prog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.kiev.prog.dao.OrderDAO;
import ua.kiev.prog.dao.ProductDAO;
import ua.kiev.prog.entity.Product;
import ua.kiev.prog.model.CartInfo;

import java.util.List;

@Service
public class MainService {
    @Autowired
    private ProductDAO productDao;

    @Autowired
    protected OrderDAO orderDAO;

    public List<Product> getProductList() {
        List<Product> productList = productDao.productList();
        return productList;
    }

    public Product findProduct(String code) {
        Product product = productDao.findProduct(code);
        return product;
    }

    public void saveOrder(CartInfo cartInfo) {
        orderDAO.saveOrder(cartInfo);
    }

    public void updateOrder(CartInfo cartInfo) {
        orderDAO.updateOrder(cartInfo);
    }

}
