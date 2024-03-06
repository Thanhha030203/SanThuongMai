package com.poly.DATN_BookWorms.rest.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("rest")
public class AuthorityRestController {
//	@Autowired
//	AuthoritiesService authorityService;
//	
//	@GetMapping("authorities")
//	public List<Authorities> findAll(@RequestParam("admin") Optional<Boolean> admin){
//		if(admin.orElse(false)) {
//			return authorityService.findAuthoritiesOfAdministrators();
//		}
//		
//		return authorityService.findAll();
//	}z
//	
//	@GetMapping("authoritiesOne")
//	public List<Authorities> getOneByRole(@RequestParam("username") String username){
//		return authorityService.getOneByRole(username);
//	}
//	
//	@PostMapping("authorities")
//	public Authorities post(@RequestBody Authorities authority) {
//		return authorityService.create(authority);
//	}
//	
//	@DeleteMapping("authorities/{id}")
//	public void delete(@PathVariable("id")String id) {
//		authorityService.delete(id);	
//	}
//	
	
}
