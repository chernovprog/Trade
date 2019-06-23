package ua.kiev.prog.util;

import ua.kiev.prog.model.CartInfo;

import javax.servlet.http.HttpServletRequest;

public class Utils {

    public static CartInfo getCartInSession(HttpServletRequest request) {

        CartInfo cartInfo = (CartInfo) request.getSession().getAttribute("cartInfo");

        if (cartInfo == null) {
            cartInfo = new CartInfo();
            request.getSession().setAttribute("cartInfo", cartInfo);
        }

        return cartInfo;
    }

    public static void removeCartInSession(HttpServletRequest request) {
        request.getSession().removeAttribute("cartInfo");
    }

    public static void storeLastOrderedCartInSession(HttpServletRequest request, CartInfo cartInfo) {
        request.getSession().setAttribute("lastOrderedCart", cartInfo);
    }

    public static CartInfo getLastOrderedCartInSession(HttpServletRequest request) {
        return (CartInfo) request.getSession().getAttribute("lastOrderedCart");
    }
}
