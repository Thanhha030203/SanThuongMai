package com.poly.DATN_BookWorms.service.impl;

import com.poly.DATN_BookWorms.utils.CRC32_SHA256;

import java.util.List;

import com.poly.DATN_BookWorms.utils.SessionService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.poly.DATN_BookWorms.dto.AccountDTO;

import com.poly.DATN_BookWorms.entities.Account;
import com.poly.DATN_BookWorms.entities.Authorities;
import com.poly.DATN_BookWorms.entities.Roles;
import com.poly.DATN_BookWorms.repo.AccountRepo;
import com.poly.DATN_BookWorms.repo.AuthoritiesRepo;
import com.poly.DATN_BookWorms.repo.RoleRepo;

import com.poly.DATN_BookWorms.service.AccountService;
import com.poly.DATN_BookWorms.service.RoleService;

@Service
public class AccountServiceImp implements AccountService {

	private static final Logger logger = LogManager.getLogger();

	@Autowired
	AccountRepo accountRepo;

	@Autowired
	RoleService roleService;

	@Autowired
	AuthoritiesRepo authoritiesRepo;

	@Autowired
	RoleRepo roleRepo;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	CRC32_SHA256 crc32Sha256;

	@Autowired
	SessionService sessionService;

	@Override
	public Account findByUserId(String userId) {
		logger.info(" find account with userId : {}", userId);
		return accountRepo.findByUserid(userId);
	}

	@Override
	public Account findByUsename(String username) {
		logger.info(" find account with username : {}", username);
		return accountRepo.findByUsername(username);
	}

	@Override
	public Account findByEmail(String email) {
		logger.info(" find account with email : {}", email);
		return accountRepo.findByEmail(email);
	}

	@Override
	public List<Account> getAdministrators() {
		return null;
	}

	@Override
	public List<Account> findAll() {
		// TODO Auto-generated method stub
		return accountRepo.findAll();
	}

	@Override
	public Account create(Account account) {
		return accountRepo.save(account);
	}

	@Override
	public Account update(Account account) {
		logger.info("update account  with account : {}", account);
		return accountRepo.save(account);
	}

	@Override
	public Account changePassword(String password, String username) {
		logger.info("changepassword  with password : {} and username : {}", password, username);
		return accountRepo.changePassword(password, username);
	}

	@Override
	public void delete(String username) {

	}

	@Override

	public void save(AccountDTO accountDTO) {
		logger.info("save AccountDTO  with AccountDTO : {}", accountDTO);
		// Get Role
		try {

			Roles role = roleRepo.findById("GUEST").get();

			if (role == null) {
				role = roleRepo.save(new Roles("GUEST", "Guest", null));
			}

			// Băm userId and authorrityId
			String userId = crc32Sha256.getCodeCRC32C(accountDTO.getUsername());
			String authorityId = crc32Sha256.getCodeCRC32C(accountDTO.getUsername() + role.getRoleid());

			logger.info(".. userId : {} and authorityId : {}", userId, authorityId);
			// Set info to account form DTO
			Account account = new Account();
			logger.info(" account : {}", account.toString());
			account.setUserid(userId);
			account.setEmail(accountDTO.getEmail());
			account.setFullname(accountDTO.getFullname());
			account.setPassword(passwordEncoder.encode(accountDTO.getPassword()));
			account.setUsername(accountDTO.getUsername());

			// Create account and author for account
			accountRepo.save(account);
			authoritiesRepo.save(new Authorities(authorityId, account, role));
			logger.info("Save accountDTO successfully with account : {}", account);
		} catch (Exception e) {
			logger.info("Save accountDTO failed with accountDTO : {}", accountDTO);
			// TODO: handle exception
		}

	}

	@Override
	public void save(Account user) {
		accountRepo.save(user);
	}

}
