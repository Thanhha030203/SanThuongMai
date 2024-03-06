package com.poly.DATN_BookWorms.controller;

import com.poly.DATN_BookWorms.entities.*;
import com.poly.DATN_BookWorms.service.AccountService;
import com.poly.DATN_BookWorms.service.AddressService;
import com.poly.DATN_BookWorms.service.BookingService;
import com.poly.DATN_BookWorms.service.DetailBookingService;
import com.poly.DATN_BookWorms.service.DiscountCodeService;
import com.poly.DATN_BookWorms.service.FileShopService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.poly.DATN_BookWorms.utils.CRC32_SHA256;
import com.poly.DATN_BookWorms.utils.SessionService;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/myAccount")

public class MyAccountController {


    @Autowired
    HttpServletRequest httpServletRequest;
    @Autowired
    CRC32_SHA256 crc;
    @Autowired
    BookingService bookingService;
    @Autowired
    HttpServletRequest req;

    @Autowired
    CRC32_SHA256 crc32_SHA256;

    @Autowired
    SessionService sessionService;

    @Autowired
    AccountService accountService;

    @Autowired
    @Qualifier("detailBookingServiceImp")
    DetailBookingService detailBookingService;

    @Autowired
    AddressService addressService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    DiscountCodeService discountCodeService;

    @Autowired
    FileShopService fileShopService;

    @GetMapping("/myPersonal")
    public String myPersonal(Model model) {
        Account account = sessionService.get("user");
        model.addAttribute("account", account);
        return "Client/My_account/MyPersonal";
    }

    @PostMapping("/changePassword/{username}")
    public String changePassword(Model model, @PathVariable String username, @RequestParam String OldPassword,
                                 @RequestParam String NewPassword,
                                 @RequestParam String confirmNewPassword) {
        Account user = sessionService.get("user");
        if (passwordEncoder.matches(OldPassword, user.getPassword())) {
//            if (!NewPassword.equals(confirmNewPassword) || confirmNewPassword!=null) {
//                model.addAttribute("messageChangePassword", "New Password and Confirm Password do not match");
//            model.addAttribute("messageChangePassword", "Error");
            if (NewPassword.equals(confirmNewPassword) && confirmNewPassword != "") {
                user.setPassword(passwordEncoder.encode(NewPassword));
                accountService.update(user);
                model.addAttribute("notiChangePassword", "Password changed successfully");
                return "redirect:/account/logout";
            } else {
                model.addAttribute("notiChangePassword", "New password and ConfirmPassword not confirm");
                return "forward:/myAccount/changePassword";
            }
        } else {
            model.addAttribute("notiChangePassword", "Old password is incorrect");
            return "forward:/myAccount/changePassword";
        }

    }

    @PostMapping("/updateMypersonal/{username}")
    public String updateMypersonal(@Valid @ModelAttribute("account") Account accountUpdate, BindingResult bindingResult,
                                   @RequestParam("fileImage") Optional<MultipartFile> multipartFile) throws Exception {
        Account user = sessionService.get("user");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        if (user != null) {
            if (bindingResult.hasErrors()) {
                System.out.println("Error");
                return "redirect:/myAccount/myPersonal";
            } else if (!bindingResult.hasErrors()) {
                if (accountUpdate.getFullname() != null) {
                    user.setFullname(accountUpdate.getFullname());
                }
                if (accountUpdate.getEmail() != null) {
                    user.setEmail(accountUpdate.getEmail());
                }
                if (accountUpdate.getGender() != null) {
                    user.setGender(accountUpdate.getGender());
                }
                if (accountUpdate.getAge() != null) {
                    user.setAge(accountUpdate.getAge());
                }
                System.out.println("file" + multipartFile);
            }
            if (multipartFile.isPresent()) {
                MultipartFile file = multipartFile.get();

                String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
                System.out.println("1" + fileName);
                String uploadDir = "./src/main/resources/static/Client/images";
                Path uploadPath = Paths.get(uploadDir);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                try {
                    InputStream inputStream = file.getInputStream();
                    Path filePath = uploadPath.resolve(fileName);
                    if (!fileName.isEmpty()) {
                        Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                        // Save the new profile image only if it is different
                        user.setImage(fileName);
                    }
                } catch (IOException e) {
                    throw new IOException("Could not  save uploaded file: " + fileName);
                }
            }
            accountService.update(user);

        }
        return "redirect:/myAccount/myPersonal";
    }

//    @ModelAttribute("gender")
//    public Map<Boolean, String> getGender(){
//        Map<Boolean,String> map = new HashMap<>();
//        map.put(true,"Male");
//        map.put(false, "Female");
//        return map;
//    }

    @RequestMapping("/address")
    public String address(Model model) {

        Account account = sessionService.get("user");
        model.addAttribute("account", account);
        List<Addressusers> ad = addressService.findByUserId(account.getUserid());
        model.addAttribute("ad", ad);
        Addressusers addressusers = new Addressusers();
        model.addAttribute("adr", addressusers);
        return "Client/My_account/Address";
    }


    @PostMapping("/newAddress")
    public String newAddress(Model model, @ModelAttribute("adr") Addressusers addressSave, @RequestParam String fullNameNewAddress, @RequestParam String numberPhoneNewAddress,
                             @RequestParam String province, @RequestParam String district,
                             @RequestParam String ward, @RequestParam String specificAddressNewAddress) {
        Account user = sessionService.get("user");
        Addressusers addressusers = new Addressusers();
        model.addAttribute("adr", addressusers);
        if (user != null) {

            addressusers.setUserid(user.getUserid());
            addressusers.setFullname(fullNameNewAddress);
            addressusers.setPhonenumber(numberPhoneNewAddress);
            System.out.println(specificAddressNewAddress + ", " + ward + ", " + district + ", " + province);
            addressusers.setDetailhome(specificAddressNewAddress);
            addressusers.setWard(ward);
            addressusers.setDistrict(district);
            addressusers.setProvince(province);
            //addressusers.setAddress(specificAddressNewAddress + ", " + ward + ", " + district + ", " + province);
            addressusers.setStatusaddress(addressSave.getStatusaddress());
            addressusers.setAddressuserid(crc.getCodeCRC32C(addressusers.toString() + new Date()));
            addressService.create(addressusers);
//    		System.out.println("adID: "+ addressService.generateNewAddressId());
        }
        return "redirect:/myAccount/address";
    }

    @RequestMapping("/editAddress/{addressuserid}")
    public String editAddress(@PathVariable String addressuserid) {
        System.out.println("aaaaaaaa" + addressuserid);
        if (addressuserid != null) {

        }
        return "redirect:/myAccount/address";
    }

    @RequestMapping("/changePassword")
    public String changePassword(Model model) {
        Account account = sessionService.get("user");
        model.addAttribute("account", account);
        return "Client/My_account/ChangePassword";
    }

    @RequestMapping("/orderMyAccount")
    public String orderMyAccount(Model model) {
        Account account = sessionService.get("user");
        model.addAttribute("account", account);
        System.out.println("you " + account.getUserid());
        List<Bookings> booking = bookingService.findByUserId(account.getUserid());

        List<Bookings> bookingStatus1 = bookingService.findByUserIdAndOrderStaturs(account.getUserid(),1);
        List<Bookings> bookingStatus2 = bookingService.findByUserIdAndOrderStaturs(account.getUserid(),2);
        List<Bookings> bookingStatus3 = bookingService.findByUserIdAndOrderStaturs(account.getUserid(),3);
        List<Bookings> bookingStatus4 = bookingService.findByUserIdAndOrderStaturs(account.getUserid(),4);
        List<Bookings> bookingStatus5 = bookingService.findByUserIdAndOrderStaturs(account.getUserid(),5);
        List<Bookings> bookingStatus6 = bookingService.findByUserIdAndOrderStaturs(account.getUserid(),6);
        List<Bookings> bookingStatus7 = bookingService.findByUserIdAndOrderStaturs(account.getUserid(),7);
        List<Bookings> bookingStatus8 = bookingService.findByUserIdAndOrderStaturs(account.getUserid(),8);
//        System.out.println("list" + booking.get(0).listOfDetailbookings.get(0).books.shoponlines.listOfFiles.get(0).filename);
        model.addAttribute("booking", booking);
//        List<Bookings> booking_StatusId = bookingService.findByStatusId(tab);
        model.addAttribute("bookingStatus1", bookingStatus1);
        model.addAttribute("bookingStatus2", bookingStatus2);
        model.addAttribute("bookingStatus3", bookingStatus3);
        model.addAttribute("bookingStatus4", bookingStatus4);
        model.addAttribute("bookingStatus5", bookingStatus5);
        model.addAttribute("bookingStatus6", bookingStatus6);
        model.addAttribute("bookingStatus8", bookingStatus8);
         model.addAttribute("bookingStatus7", bookingStatus7);



        for (Bookings bookings : booking) {
            System.out.println("listsize : " + booking.size());
            System.out.println(" 74 order " + bookings.toString() + "--- img = " + bookings.listOfDetailbookings.get(0).books.shoponlines.listOfFiles.get(0).filename);
        }
        return "Client/My_account/Order";
    }



    @RequestMapping("/orderDetailMyAccount/{id}")
    public String orderDetailMyAccount(Model model, @PathVariable("id") String dbId) {
        Account account = sessionService.get("user");
        model.addAttribute("account", account);
        List<Detailbookings> a = detailBookingService.findByBookingId(dbId);
        model.addAttribute("adr", a.get(0).bookings.listOfPayments.get(0));

        model.addAttribute("db", a);
        return "Client/My_account/OrderDetail";
    }

    @RequestMapping("/voucherMyAccount")
    public String voucherMyAccount(Model model) {
        Account account = sessionService.get("user");
        model.addAttribute("account", account);
        Shoponlines shop = new Shoponlines();
        model.addAttribute("shop", shop);
        List<com.poly.DATN_BookWorms.entities.Files> listFile = fileShopService.findAll();
        //com.poly.DATN_BookWorms.entities.Files files = new com.poly.DATN_BookWorms.entities.Files();
        model.addAttribute("files", listFile);
        System.out.println("sucsess");
        List<Discountcodes> discountcodes = discountCodeService.findDisountByUserId(account.getUserid());
        model.addAttribute("discount", discountcodes);
        System.out.println("sucsess1");

        return "Client/My_account/VoucherMyAccount";
    }
}
