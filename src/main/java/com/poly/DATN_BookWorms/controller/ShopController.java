package com.poly.DATN_BookWorms.controller;

import com.poly.DATN_BookWorms.entities.Books;
import com.poly.DATN_BookWorms.entities.Categories;
import com.poly.DATN_BookWorms.entities.Discountcodes;
import com.poly.DATN_BookWorms.entities.Evaluates;
import com.poly.DATN_BookWorms.entities.Publishingcompanies;
import com.poly.DATN_BookWorms.entities.Sales;
import com.poly.DATN_BookWorms.entities.Shoponlines;
import com.poly.DATN_BookWorms.entities.Typebooks;
import com.poly.DATN_BookWorms.entities.Writtingmasters;
import com.poly.DATN_BookWorms.response.BookResponse;
import com.poly.DATN_BookWorms.service.BookService;
import com.poly.DATN_BookWorms.service.DiscountCodeService;
import com.poly.DATN_BookWorms.service.EvaluatesService;
import com.poly.DATN_BookWorms.service.SaleService;
import com.poly.DATN_BookWorms.service.ShopOnlinesService;
import com.poly.DATN_BookWorms.service.TypeBookService;
import com.poly.DATN_BookWorms.service.WriterService;
import com.poly.DATN_BookWorms.utils.CRC32_SHA256;

import jakarta.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/shop")
public class ShopController {
	
	private static final Logger logger = LogManager.getLogger();
	
    @Autowired
    ShopOnlinesService shopOnlinesService;
	@Autowired
	TypeBookService typeBookService;
	
	@Autowired
	WriterService writerService;
	
    @Autowired
    BookService bookService;
    @Autowired
    EvaluatesService evaluatesService;
    
    @Autowired
    SaleService saleService;
    
    @Autowired
    DiscountCodeService discountCodeService;

    @Autowired
    HttpServletRequest req;
    
    @Autowired
    CRC32_SHA256 crc32_SHA256;
    
    
   
    
    @GetMapping("/{id}")
    public String profile(Model model, @PathVariable("id") Integer id,
                          @RequestParam(defaultValue = "0") int page,
                          @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Shoponlines list = shopOnlinesService.findById(id);
        Integer total = evaluatesService.sumDbidByEvaluateId(id);
        model.addAttribute("total", total);
        model.addAttribute("profile", list);
        Page<Books> bookPage = bookService.findByshopid(id,pageable);
        model.addAttribute("books", bookPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", bookPage.getTotalPages());
        List<Books> minquantity  = bookService.findTop5LowestQuantityBooksByShopId(id);      
        model.addAttribute("min quantity", minquantity);
        List<Writtingmasters> listWriter = writerService.getWrittingWithSHop(id);
        model.addAttribute("listWriter", listWriter);      
       List<Categories> listCateogories = typeBookService.getCategoriesWithShop(id);
        model.addAttribute("listCateogories", listCateogories);     
        List<Publishingcompanies> plcm = bookService.getPCWithShop(id);
        model.addAttribute("plcm", plcm);
        
        if(req.getRemoteUser() == null) { 
            List<Sales> saleByShopAndByIntendFor = null;
            model.addAttribute("listSaleOfshop", saleByShopAndByIntendFor);
            List<Discountcodes> findDisountOfShopWithUser =null;
            model.addAttribute("dis", findDisountOfShopWithUser);
        }
        else { 
            List<Sales> saleByShopAndByIntendFor = saleService.saleByShopAndByIntendFor(id, "D");
            model.addAttribute("listSaleOfshop", saleByShopAndByIntendFor);
            List<Discountcodes> findDisountOfShopWithUser = discountCodeService.findDisountOfShopWithUser( "D", crc32_SHA256.getCodeCRC32C(req.getRemoteUser()), id);
            model.addAttribute("dis", findDisountOfShopWithUser);
        }
        
        return "Client/Product_page/product_shop_list";
    }
}
