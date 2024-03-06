package com.poly.DATN_BookWorms.rest.controller;

import com.poly.DATN_BookWorms.entities.Account;
import com.poly.DATN_BookWorms.entities.AddressShop;
import com.poly.DATN_BookWorms.entities.Shoponlines;
import com.poly.DATN_BookWorms.service.AddressShopService;
import com.poly.DATN_BookWorms.service.ShopService;
import com.poly.DATN_BookWorms.utils.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/shop")
public class ShopRestController {
    @Autowired
    ShopService shopService;
    @Autowired
    SessionService sessionService;

    @Autowired
    AddressShopService addressShopService;


    @GetMapping("/detail")
    public ResponseEntity<Shoponlines> getShopDetail() {
        Account user = sessionService.get("user");
        Shoponlines shopDetail = shopService.findUserId(user.getUserid());
        return ResponseEntity.ok(shopDetail);
    }

    @GetMapping("/account")
    public ResponseEntity<Account> getAccountShop() {
        Account user = sessionService.get("user");
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<Shoponlines> createUser(@RequestBody @Valid Shoponlines shoponlines) {
        // Lưu đối tượng người dùng vào cơ sở dữ liệu
        shopService.save(shoponlines);
        // Trả về phản hồi thành công
        return ResponseEntity.ok(shoponlines);
    }

    @PostMapping("/saveInfoShop")
    public ResponseEntity<Shoponlines> saveInfoShop(@RequestBody @Valid Shoponlines shoponlines) {
        // Lưu thay đổi thông tin shop vào SQL Serer
        shopService.save(shoponlines);
        // Trả về phản hồi thành công
        return ResponseEntity.ok(shoponlines);
    }

    @PostMapping(value = "/save/profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void saveProfileChange(@RequestParam(value = "fileImage") Optional<MultipartFile> multipartFile, @RequestParam("shopId") String shopId) throws Exception {
        if (!multipartFile.isPresent()) {
            Shoponlines shoponlines = shopService.findById(Integer.parseInt(shopId));
            shopService.save(shoponlines);
        } else {
            MultipartFile file = multipartFile.get();
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            String uploadDir = "D:/DATN/DATN_BookWorms/src/main/resources/static/SellerChannel/images/";
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            try {
                InputStream inputStream = file.getInputStream();
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                //save change profile
                Shoponlines shoponlines = shopService.findById(Integer.parseInt(shopId));
                
                //shoponlines.setLogo(fileName);
                shopService.save(shoponlines);
            } catch (IOException e) {
                throw new IOException("Could not  save uploaded file: " + fileName);
            }
        }


    }

    @GetMapping("/address")
    public ResponseEntity<List<AddressShop>> getAddressShop() {
        Account user = sessionService.get("user");
        Shoponlines shopDetail = shopService.findUserId(user.getUserid());
        List<AddressShop> addressShops = addressShopService.findByShopid(shopDetail.getShopid());
//        System.out.println(addressShop.getAddressshopid());
        return ResponseEntity.ok(addressShops);
    }

    @PostMapping(value = "/address/createOrUpdate", produces = MediaType.APPLICATION_JSON_VALUE)
    public void saveAddressShop(@RequestBody AddressShop addressShop) throws IOException {
        Date now = new Date();
        addressShop.setCreateat(now);
        addressShop.setIsactive(true);
        // Shop user there
        Account user = sessionService.get("user");
        Shoponlines shopDetail = shopService.findUserId(user.getUserid());
        addressShop.setShoponlines(shopDetail);
        //save address default
        addressShopService.save(addressShop);
    }

    @PostMapping(value = "/address/updateActive", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void saveChangeActive(@RequestParam("addressShopId") String addressShopId) throws IOException {
        AddressShop addressShop = addressShopService.findById(Integer.parseInt(addressShopId));
        addressShop.setIsactive(!addressShop.getIsactive());
        addressShopService.save(addressShop);
    }
}
