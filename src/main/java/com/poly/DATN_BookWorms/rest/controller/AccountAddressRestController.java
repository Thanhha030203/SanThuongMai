package com.poly.DATN_BookWorms.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.DATN_BookWorms.entities.Addressusers;
import com.poly.DATN_BookWorms.service.AccountAddressService;
import com.poly.DATN_BookWorms.service.AddressService;
import com.poly.DATN_BookWorms.utils.CRC32_SHA256;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin("*")
@RequestMapping("/rest/address")
public class AccountAddressRestController {

	@Autowired
	CRC32_SHA256 crc32_SHA256;
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	AccountAddressService accountAddressService;
	
	@Autowired
    AddressService addressService;

	@GetMapping
	public List<Addressusers> getAddressByUser(){ 
		String userid = crc32_SHA256.getCodeCRC32C(request.getRemoteUser());
		System.out.println("in ra "+accountAddressService.getAdressByUser(userid) );
		return accountAddressService.getAdressByUser(userid);
	}
	
	@GetMapping("/{id}")
	public Addressusers getAddressById(@PathVariable String id ) {
		return addressService.byAddressUserId(id);
	}
	

	@PostMapping
	public Addressusers postAddress(@RequestBody Addressusers json){ 
		
		return addressService.update(json);
	}
	
	@DeleteMapping("/{id}")
	public void deleteAddress(@PathVariable String id ){ 
		
		addressService.delete(id);
	}

	@PutMapping("/update")
	public Addressusers ok(@RequestBody String id){
		Addressusers add = accountAddressService.findById(id);

		String userid = crc32_SHA256.getCodeCRC32C(request.getRemoteUser());
		List<Addressusers> as = accountAddressService.getAdressByUser(userid);
		for (Addressusers addressusers : as) {
			addressusers.setStatusaddress("Không");
			accountAddressService.save(addressusers);
		}
		add.setStatusaddress("Mặc định");
		accountAddressService.save(add);
		return add ;
	}
}
