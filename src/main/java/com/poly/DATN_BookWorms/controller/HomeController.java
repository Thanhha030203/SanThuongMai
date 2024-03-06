package com.poly.DATN_BookWorms.controller;

import com.itextpdf.text.pdf.qrcode.Mode;
import com.poly.DATN_BookWorms.entities.*;
import com.poly.DATN_BookWorms.repo.AuthoritiesRepo;
import com.poly.DATN_BookWorms.service.*;
import com.poly.DATN_BookWorms.utils.CRC32_SHA256;
import com.poly.DATN_BookWorms.utils.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/Ibook")
public class HomeController {
    @Autowired
    BookService bookService;

    @Autowired
    SessionService sessionService;

    @Autowired
    RoleService roleService;

    @Autowired
    AuthoritiesService authoritiesService;

    @Autowired
    CRC32_SHA256 crc32Sha256;

    @Autowired
    ShopService shopService;
    
    @Autowired
    ShopOnlinesService shopOnlinesService;

    @Autowired
    AccountService accountService;

    @RequestMapping("/index")
    public String home(Model model, Authentication authentication) {
        //get user on session
        Account user = sessionService.get("user");

        //get and set info home page
        Books top = bookService.findTopBookByQuantitySold();
        List<Imagebooks> listImage = top.getListOfImagebooks();

        model.addAttribute("item", top);

        model.addAttribute("listImage", listImage);

        List<Books> education = bookService.getBooksByCategoryID(2);
        List<Books> education1 = bookService.getBooksByCategoryID(4);
        List<Books> education2 = bookService.getBooksByCategoryID(7);
        model.addAttribute("education", education);
        model.addAttribute("education1", education1);
        model.addAttribute("education2", education2);
        if (education != null && !education.isEmpty()) {
            for (Books educationItem : education) {
//                System.out.println("Tên sản phẩm: " + educationItem.getBookname());
//                System.out.println("Giá sản phẩm: " + educationItem.getPrice());
                System.out.println();
            }
        } else {
            System.out.println("Không có dữ liệu.");
        }



        //request info user to header
        header(model, user);
        //

        model.addAttribute("user", user);
        System.out.println(user.getUserid());
        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
            return "redirect:/admin/index";
        } else {
            return "Client/header_footer_index/aaa";
        }
    
    }

    @RequestMapping("/header")
    public String header(Model model, Account user) {
        if (user !=null){
            model.addAttribute("image", user.getImage());
            model.addAttribute("name", user.getFullname());
            Roles roles = roleService.findSellerByRoleId("SELLER");

            Shoponlines shoponlines = shopService.findUserId(user.getUserid());
            model.addAttribute("shop", shoponlines);
        }
        return "Client/header_footer_index/header_index";
    }

    @RequestMapping("/seller")
    public String seller(Model model) {
        Account user = sessionService.get("user");
        if (user == null) {
            return "redirect:/account/login";
        } else {
            //get data shop
            Shoponlines shoponlines = shopService.findUserId(user.getUserid());
            model.addAttribute("shop", shoponlines);

            return "SellerChannel/index";
        }
    }

    @RequestMapping("/createSeller")
    public String newSeller(Model model) {
        Account user = sessionService.get("user");

        // add role SEller if dont have ROLE SELLER
        Roles role = roleService.findSellerByRoleId("SELLER");
        if (role == null) {
            role = roleService.save(new Roles("SELLER", "Seller", null));
        }
        //Create ROLE SELLER TO USER
        String authorityId = crc32Sha256.getCodeCRC32C(user.getUsername() + role.getRoleid());
        authoritiesService.save(new Authorities(authorityId, user, role));

        //create default shop with account
        if ( user.getListOfShoponlines().isEmpty()|| shopService.findById(user.getListOfShoponlines().get(0).getShopid())==null ) {
            shopService.createShopDefaultWithUser(user);
        }

        //Add Account to Session again
        Account user2 = accountService.findByUserId(user.getUserid());
        sessionService.set("user",user2);

        //get data shop
        Shoponlines shoponlines = shopService.findUserId(user2.getUserid());
        model.addAttribute("shop", shoponlines);
        return "SellerChannel/index";
    }

    @GetMapping("/shop")
    public String getAllShop(Model model) { 
    	List<Shoponlines> listshop = new ArrayList<Shoponlines>();
    	listshop = shopOnlinesService.getAllListShop();
    	model.addAttribute("shoplist", listshop);
    	 return "Client/Product_page/shop_list";
    }

    @GetMapping("/about")
    public String getBout(Model model) { 
 	
    	 return "Client/header_footer_index/about";
    }
}
