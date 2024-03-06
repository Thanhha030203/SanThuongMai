package com.poly.DATN_BookWorms.rest.controller;


import com.poly.DATN_BookWorms.entities.Account;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.poly.DATN_BookWorms.service.AccountService;
import com.poly.DATN_BookWorms.utils.SessionService;

import java.util.Optional;


@CrossOrigin("*")
@RestController
@RequestMapping("/rest")
public class AccountRestController {
	
	private static final Logger logger = LogManager.getLogger();
	@Autowired
	AccountService accountService;
	
    @Autowired
    PasswordEncoder passwordEncoder;
	
	@Autowired
	SessionService sessionService;
	
	 @PostMapping("/otp")
	  public String confirmOTP(@RequestBody String otp) { 
		  int OTP = sessionService.get("OTP");
		  System.out.println("otp alf "+ otp);
		  if(OTP == Integer.parseInt(otp)) { 
			  return "OK";
		  }
		  else { 
			  return "NOT";
		  }
      
   }
	 
		
	 @PostMapping("/account/newpass")
	  public Account newpass(@RequestBody String password) {
		 logger.info("New Password RequestBody {}", password);
		  System.out.println("otp alf "+ password);
		 Account account = sessionService.get("acc");
		 logger.info("Account for change pass : {}", account);
		 try {
			account.setPassword(passwordEncoder.encode(password));
			accountService.create(account);
			logger.info("New Password is success with account : {}", account);
			return account;
		} catch (Exception e) {
			logger.error("New Password is failed with error : {}", e);
			return null;
		}
   }
	
//	@GetMapping("/accounts")
//	public List<Account> getAccounts(@RequestParam("admin") Optional<Boolean> admin){
//		if(admin.orElse(false)) {
//			return accountService.getAdministrators();
//		}
//		return accountService.findAll();
//	}
	
//	@GetMapping("accounts")
//	public List<Account> getAccounts(@RequestParam("admin") Optional<Boolean> admin){
//		if(admin.orElse(false)) {
//			return accountService.getAdministrators();
//		}
//		return accountService.findAll();
//	}
//	
//	@PostMapping("accountsManage")
//	public Account create(@RequestBody AccountDTO accountDTO) {
//		Account account  = accountService.create(accountDTO);
//		return account;
//	}
//	
	// @PutMapping("accounts/{id}")
	// public Account update(@RequestBody Account account, @PathVariable("id") String username) {
	// 	return accountService.update(account);
	// }
//	
//	@DeleteMapping("accounts/{id}")
//	public void delete(@PathVariable("id") String username) {
//		accountService.delete(username);
//	}
}
