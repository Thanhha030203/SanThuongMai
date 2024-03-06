package com.poly.DATN_BookWorms.controller;

import javax.validation.Valid;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.mail.MessagingException;

import com.poly.DATN_BookWorms.dto.AccountDTO;
import com.poly.DATN_BookWorms.entities.Account;
import com.poly.DATN_BookWorms.service.AccountService;
import com.poly.DATN_BookWorms.service.CustomUserDetailService;

import com.poly.DATN_BookWorms.service.MailService;
import com.poly.DATN_BookWorms.utils.CRC32_SHA256;
import com.poly.DATN_BookWorms.utils.MailBody;
import com.poly.DATN_BookWorms.utils.OTP_privateKey;
import com.poly.DATN_BookWorms.utils.SessionService;

@Controller
@RequestMapping("/account")
public class    AccountController {

    @Autowired
    AccountService accountService;

    @Autowired
    CustomUserDetailService customUserDetailService;

    @Autowired
    MailService mailService;
    
    @Autowired
    OTP_privateKey otp_privateKey;
    
    @Autowired
    SessionService sessionService;
    
    @Autowired
    MailBody mailBody;

    @Autowired
    CRC32_SHA256 crc32_SHA256;

    @RequestMapping("/login")
    public String loginForm() {
        return "/Client/Account_page/Login";
    }

    @RequestMapping("/login-google/success")
    public String loginWithGoogle(@AuthenticationPrincipal OAuth2User performance, Model model) {
        if (performance == null) {
            return "redirect:/login";
        }

        if (accountService.findByUsename(performance.getName()) == null) {
            AccountDTO accountDTO = new AccountDTO();
            accountDTO.setEmail(performance.getAttribute("email"));
            accountDTO.setUsername(performance.getName());
            accountDTO.setFullname(performance.getAttribute("name"));
            accountDTO.setPassword(String.valueOf(RandomStringUtils.randomAlphabetic(8)));
            accountService.save(accountDTO);

        }
        try {
            // Sleep for 2 seconds (2000 milliseconds)
            Thread.sleep(5000);
            customUserDetailService.loadUserByUsername(performance.getName());

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "redirect:/Ibook/index";
    }

    @RequestMapping("/login-facebook/success")
    public String loginWithFaceBook(@AuthenticationPrincipal OAuth2User performance, Model model) {
        if (performance == null) {
            return "redirect:/login";
        }
        System.out.println(performance.toString());
        if (accountService.findByUsename(performance.getName()) == null) {
            AccountDTO accountDTO = new AccountDTO();
            accountDTO.setEmail("");
            accountDTO.setUsername(performance.getAttribute("id"));
            accountDTO.setFullname(performance.getAttribute("name"));
            accountDTO.setPassword(String.valueOf(RandomStringUtils.randomAlphabetic(8)));
            accountService.save(accountDTO);

        }

        try {
            // Sleep for 2 seconds (2000 milliseconds)
            Thread.sleep(5000);
            customUserDetailService.loadUserByUsername(performance.getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "redirect:/Ibook/index";
    }


    @GetMapping("/registration")
    public String registrationForm(Model model) {
        AccountDTO user = new AccountDTO();
        model.addAttribute("user", user);
        return "Client/Account_page/Register";
    }

    @PostMapping("/registration")
    public String registration(@Valid @ModelAttribute("user") AccountDTO accountDTO, BindingResult result, Model model) {
        Account existingUser = accountService.findByUsename(accountDTO.getUsername());

        if (existingUser != null)
            result.rejectValue("Username", null, "User already registered !!!");

        if (result.hasErrors()) {
            model.addAttribute("user", accountDTO);
            return "Client/Account_page/Register";
        }
        accountService.save(accountDTO);
        return "redirect:login";
    }


    @GetMapping("/forgotPassword")
    public String forgotPassword(Model model) {
        return "Client/Account_page/ForgotPassword";
    }

    
      @PostMapping("/forgotPasswordAction")
    public String forgotPassword(@RequestParam("username") String username,Model model) {
    	  System.out.println("lỗi khi gửi mail: ");
    	  String userid = crc32_SHA256.getCodeCRC32C(username);
    	  Account account = accountService.findByUserId(userid);
    	  if(account == null) { 
    		   return "Client/Account_page/ForgotPassword";
    	  }
    	  else { 
    		  try {
    			  int OTP = otp_privateKey.OTP();
    			  String subject ="XÁC NHẬN DANH TÍNH NGƯỜI SỬ DỤNG IBOOK";
    			  String body = mailBody.mailBody(account.getFullname(), OTP);
				mailService.send(account.getEmail(),subject, body);
				sessionService.set("OTP", OTP);
				System.out.println("gui thành coong");
				sessionService.set("acc", account);
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				System.out.println("lỗi khi gửi mail: "+e);
			}
    		  
    		  return "Client/Account_page/ConfirmCode";
    	  }
    	  
    	
    	  }
      
      @GetMapping("/newpass")
      public String newPass() { 
    	  return "Client/Account_page/newPassword"; 
      }
      
      
      @GetMapping("/otpcon")
      public String otpcon() { 
    	  return "Client/Account_page/ConfirmCode"; 
      }  
}
