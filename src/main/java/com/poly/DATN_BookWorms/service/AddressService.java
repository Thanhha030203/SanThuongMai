package com.poly.DATN_BookWorms.service;

import com.poly.DATN_BookWorms.entities.Addressusers;

import java.util.List;

public interface AddressService {
    List<Addressusers> findByAll();

    public List<Addressusers> findByUserId(String userId);
    
    public Addressusers create(Addressusers addressusers);
    
    public Addressusers update(Addressusers addressusers);
    
    public void delete(String addressusers);
    
//    public String generateNewAddressId();
    
    public Addressusers byAddressUserId(String addressusersId);
    
}
