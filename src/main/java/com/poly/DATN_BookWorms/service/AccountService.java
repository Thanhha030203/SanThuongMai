package com.poly.DATN_BookWorms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.poly.DATN_BookWorms.dto.AccountDTO;
import com.poly.DATN_BookWorms.entities.Account;


@Service
public interface AccountService {

	public  Account findByUserId(String userId);
	public Account findByUsename(String username);
	
	public Account findByEmail(String email);

	public List<Account> getAdministrators();

	public List<Account> findAll();

	public Account create(Account account);

	public Account update(Account account);

	public Account changePassword(String password, String username);
	void delete(String username);

	void save(AccountDTO accountDTO);

    void save(Account user);


//	public void loginFromOAuth2(OAuth2AuthenticationToken oauth2);
	
}
