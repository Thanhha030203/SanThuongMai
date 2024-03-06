package com.poly.DATN_BookWorms.controller;

import com.poly.DATN_BookWorms.entities.Account;
import com.poly.DATN_BookWorms.entities.AddressShop;
import com.poly.DATN_BookWorms.entities.Shoponlines;
import com.poly.DATN_BookWorms.service.AddressShopService;
import com.poly.DATN_BookWorms.service.ShopService;
import com.poly.DATN_BookWorms.utils.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Ibook/seller")
public class SellerController {
    @Autowired
    ShopService shopService;
    @Autowired
    SessionService sessionService;

    @Autowired
    AddressShopService addressShopService;
    @RequestMapping("/index")
    public String seller(Model model){
        return "SellerChannel/index";
    }
    @RequestMapping("/homePageSeller")
    public String homePageSeller(Model model){
        return "SellerChannel/homePageSeller";
    }
    @RequestMapping("/orderManagement/sales")
    public String salesOrderManagement(Model model){
        return "SellerChannel/Sales";
    }
    @RequestMapping("/shop/shopProfile")
    public String shopProfile(Model model){
        return "SellerChannel/ProfileShop";
    }

    @RequestMapping("/shop/shopProfile/change")
    public String changeProfile(Model model){
        return "SellerChannel/ChangeProfile";
    }

    @RequestMapping("/shop/setting/address")
    public String addressSetting(Model model){
        return "SellerChannel/AddressSetting";
    }
    @RequestMapping("/shop/setting/shipping")
    public String shippingSetting(Model model){
        return "SellerChannel/ShippingSetting";
    }
    @RequestMapping("/shop/setting/account")
    public String accountSetting(Model model){
        return "SellerChannel/ProfileNofication";
    }
    @RequestMapping("/shop/sales")
    public String sales(Model model){
        return "SellerChannel/DashboardSales";
    }
    @RequestMapping("/shop/finance/revenue")
    public String revenueFinance(Model model){
        return "SellerChannel/FinanceRevenue";
    }
    @RequestMapping("/shop/TranportChannel/CreateProduct")
    public String createProduct(Model model){
        return "SellerChannel/CreateProduct";
    }
    @RequestMapping("/shop/TranportChannel/Transport")
    public String Tranport(Model model){
        return "SellerChannel/TransportChannel";
    }
    @RequestMapping("/shop/TranportChannel/Voucher")
    public String Voucher(Model model){
        return "SellerChannel/Voucher";
    }
    @RequestMapping("/shop/TranportChannel/CreateVoucher")
    public String createVoucher(Model model){
        return "SellerChannel/CreateVoucher";
    }
    @RequestMapping("/shop/TranportChannel/ShopRewiew")
    public String rewiews(Model model){
        return "SellerChannel/ShopRewiew";
    }
    @RequestMapping("/shop/finance/bankAccountBalance")
    public String bankAccountBalance(Model model){
        return "SellerChannel/BankAccountBalance";
    }
    @RequestMapping("/shop/finance/createBankAccount")
    public String createBankAccount(Model model){
        return "SellerChannel/CreateBankAccount";
    }
}
