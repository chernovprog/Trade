package ua.kiev.prog.model;

import java.util.ArrayList;
import java.util.List;

public class CartInfo {
    private int orderNum;
    private final List<CartLineInfo> cartLines = new ArrayList<>();

    public CartInfo() {
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }


    public List<CartLineInfo> getCartLines() {
        return cartLines;
    }

    private CartLineInfo findLineByCode(String code) {
        for (CartLineInfo line : cartLines) {
            if (line.getProductInfo().getCode().equals(code)) {
                return line;
            }
        }
        return null;
    }

    public void addProduct(ProductInfo productInfo) {
        CartLineInfo line = findLineByCode(productInfo.getCode());

        if (line == null) {
            line = new CartLineInfo();
            line.setProductInfo(productInfo);
            line.setQuantity(1);
            cartLines.add(line);
        }
    }


    public void updateProduct(String code, int quantity) {
        CartLineInfo line = findLineByCode(code);

        if (line != null) {
            if (quantity <= 0) {
                cartLines.remove(line);
            } else {
                line.setQuantity(quantity);
            }
        }
    }

    public void removeProduct(ProductInfo productInfo) {
        CartLineInfo line = findLineByCode(productInfo.getCode());
        if (line != null) {
            cartLines.remove(line);
        }
    }

    public boolean isEmpty() {
        return cartLines.isEmpty();
    }

    public double getAmountTotal() {
        double total = 0;

        for (CartLineInfo line : cartLines) {
            total += line.getAmount();
        }

        return total;
    }

    public void updateQuantity(CartInfo cartForm) {
        if (cartForm != null) {
            List<CartLineInfo> lines = cartForm.getCartLines();
            for (CartLineInfo line : lines) {
                this.updateProduct(line.getProductInfo().getCode(), line.getQuantity());
            }
        }
    }
}
