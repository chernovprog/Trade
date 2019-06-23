package ua.kiev.prog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.kiev.prog.entity.Product;
import ua.kiev.prog.model.CartInfo;
import ua.kiev.prog.model.ProductInfo;
import ua.kiev.prog.service.MainService;
import ua.kiev.prog.util.Utils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Controller
public class MainController {

    @Autowired
    private MainService mainService;

    @RequestMapping("/")
    public String home() {
        return "index";
    }

    @RequestMapping("/productList")
    public String productListHandler(Model model) {
        List<Product> products = mainService.getProductList();
        model.addAttribute("products", products);

        return "productList";
    }


    @RequestMapping("/buyProduct")
    public String buyProductHandler(HttpServletRequest request, @RequestParam(name = "code") String code) {
        Product product = null;

        if (isNotEmpty(code)) {
            product = mainService.findProduct(code);
        }

        if (product != null) {
            CartInfo cartInfo = Utils.getCartInSession(request);
            ProductInfo productInfo = new ProductInfo(product);
            cartInfo.addProduct(productInfo);
        }

        return "redirect:/shoppingCart";
    }

    @RequestMapping({"/removeProduct"})
    public String removeProductHandler(HttpServletRequest request, @RequestParam(name = "code") String code) {
        Product product = null;

        if (isNotEmpty(code)) {
            product = mainService.findProduct(code);
        }

        if (product != null) {
            CartInfo cartInfo = Utils.getCartInSession(request);
            ProductInfo productInfo = new ProductInfo(product);
            cartInfo.removeProduct(productInfo);
        }

        return "redirect:/shoppingCart";
    }

    @GetMapping(value = {"/shoppingCart"})
    public String shoppingCartHandler(HttpServletRequest request, Model model) {
        CartInfo cartInfo = Utils.getCartInSession(request);
        model.addAttribute("cartForm", cartInfo);

        return "shoppingCart";
    }

    @PostMapping(value = {"/shoppingCart"})
    public String shoppingCartUpdateQuantity(HttpServletRequest request,
                                             @ModelAttribute("cartForm") CartInfo cartForm) {
        if (cartForm != null) {
            CartInfo cartInfo = Utils.getCartInSession(request);
            cartInfo.updateQuantity(cartForm);
        }

        return "redirect:/shoppingCart";
    }


    @RequestMapping(value = {"/shoppingCartConfirmation"})
    public String shoppingCartConfirmation(HttpServletRequest request) {
        CartInfo cartInfo = Utils.getCartInSession(request);

        if (cartInfo.isEmpty()) {
            return "redirect:/shoppingCart";
        }

        try {
            mainService.saveOrder(cartInfo);
        } catch (Exception ex) {
            ex.printStackTrace();
            return "redirect:/shoppingCart";
        }

        Utils.removeCartInSession(request);
        Utils.storeLastOrderedCartInSession(request, cartInfo);

        return "shoppingCartFinalize";
    }

    @RequestMapping(value = {"/editOrder"})
    public String editOrderHandler(HttpServletRequest request, Model model) {
        CartInfo cartInfo = Utils.getLastOrderedCartInSession(request);

        if (cartInfo == null || cartInfo.isEmpty()) {
            return "redirect:/productList";
        }

        model.addAttribute("cartForm", cartInfo);

        return "shoppingCartEdit";
    }

    @PostMapping(value = {"/shoppingCartEdit"})
    public String editOrderUpdateQuantity(HttpServletRequest request,
                                          @ModelAttribute("cartForm") CartInfo cartForm) {
        if (cartForm != null) {
            CartInfo cartInfo = Utils.getLastOrderedCartInSession(request);
            cartInfo.updateQuantity(cartForm);
        }

        return "redirect:/editOrder";
    }

    @RequestMapping(value = {"/updateOrder"})
    public String editOrderUpdateHandler(HttpServletRequest request) {
        CartInfo cartInfo = Utils.getLastOrderedCartInSession(request);

        try {
            mainService.updateOrder(cartInfo);
        } catch (Exception ex) {
            ex.printStackTrace();
            return "redirect:/shoppingCart";
        }

        Utils.removeCartInSession(request);
        Utils.storeLastOrderedCartInSession(request, cartInfo);

        return "shoppingCartFinalize";
    }
}
