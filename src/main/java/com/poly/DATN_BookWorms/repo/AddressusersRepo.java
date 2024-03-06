package com.poly.DATN_BookWorms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.DATN_BookWorms.entities.Addressusers;

import java.util.List;

public interface AddressusersRepo extends JpaRepository<Addressusers, String>{

    @Query("select ad from Addressusers ad where ad.userid = ?1")
    List<Addressusers> findByUserId(String userId);
    
//    @Query("SELECT MAX(CAST(SUBSTRING(a.addressuserid, 3, LENGTH(a.addressuserid) - 3) AS int)) FROM Addressusers a WHERE LENGTH(a.addressuserid) > 2")
//    Integer findMaxFirstPartOfId();

    @Query(value = "SELECT MAX(CAST(SUBSTRING(a.addressuserid, CHARINDEX('UID', a.addressuserid) + 3, LEN(a.addressuserid) - CHARINDEX('UID', a.addressuserid) - 2) AS INT)) FROM Addressusers a WHERE CHARINDEX('UID', a.addressuserid) > 0", nativeQuery = true)
    Integer findMaxFirstPartOfId();

//    @Query(value = "SELECT LPAD((:maxFirstPart + 1), 2, '0')", nativeQuery = true)
//    String generateNewSecondPart(@Param("maxFirstPart") Integer maxFirstPart);
    @Query(value = "SELECT RIGHT(REPLICATE('0', 2) + CAST((CAST(?1 AS int) + 1) AS varchar), 2)", nativeQuery = true)
    String generateNewSecondPart(Integer maxFirstPart);
 
}
