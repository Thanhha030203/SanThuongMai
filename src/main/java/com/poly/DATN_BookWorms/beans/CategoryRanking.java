package com.poly.DATN_BookWorms.beans;

import com.poly.DATN_BookWorms.entities.Categories;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRanking {
    private Categories categories;
    private int orderNumbers;
    
    

	
	
    public CategoryRanking(Optional<Categories> byId, int orderNumbers) {
    }

	public Categories getCategories() {
		return categories;
	}

	public void setCategories(Categories categories) {
		this.categories = categories;
	}

	public int getOrderNumbers() {
		return orderNumbers;
	}

	public void setOrderNumbers(int orderNumbers) {
		this.orderNumbers = orderNumbers;
	}

	// public CategoryRanking(Categories categories, int orderNumbers) {
	
	// 	this.categories = categories;
	// 	this.orderNumbers = orderNumbers;
	// }

	// public CategoryRanking() {

	// }
    
    
    
}
