package com.poly.DATN_BookWorms.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.poly.DATN_BookWorms.entities.Cart;
import com.poly.DATN_BookWorms.entities.Shoponlines;


public interface CartRepo extends JpaRepository<Cart,Long> {

	@Query("Select c from Cart c where c.account.userid like ?1")
	List<Cart> findCartByUser(String userid);
	
	@Query("Select b.shoponlines from Books b where b.bookid in (Select c.books.bookid from Cart c where c.account.userid like ?1 )")
	List<Shoponlines> list_cart_shopId(String userid);
	
	//@Query("Select c from Cart c where c.userid like ?1 and c.bookid like ?2")
	
	
}
