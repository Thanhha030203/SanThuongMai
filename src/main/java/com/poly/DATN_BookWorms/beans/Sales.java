package com.poly.DATN_BookWorms.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sales {

    private double monthlySales;

    private int orders;

    private double conversionRate;

    private double pagesViews;

    private double  salesPerOrder;


    
	public double getMonthlySales() {
		return monthlySales;
	}

	public void setMonthlySales(double monthlySales) {
		this.monthlySales = monthlySales;
	}

	public int getOrders() {
		return orders;
	}

	public void setOrders(int orders) {
		this.orders = orders;
	}

	public double getConversionRate() {
		return conversionRate;
	}

	public void setConversionRate(double conversionRate) {
		this.conversionRate = conversionRate;
	}

	public double getPagesViews() {
		return pagesViews;
	}

	public void setPagesViews(double pagesViews) {
		this.pagesViews = pagesViews;
	}

	public double getSalesPerOrder() {
		return salesPerOrder;
	}

	public void setSalesPerOrder(double salesPerOrder) {
		this.salesPerOrder = salesPerOrder;
	}

}
