
package com.poly.DATN_BookWorms.entities;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;

/**
 * JPA entity class for "Account"
 *
 * @author Telosys
 *
 */

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name="Account" )
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;

    //--- ENTITY PRIMARY KEY
    @Id
    public String     userid ;

    public String     username ;

	@NotBlank(message = "Fullname is required")
    public String     fullname ;

    public String     password ;

    @Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern ="yyyy-MM-dd")
    @Column(name="Age")
    public Date       age ;

    public String     email ;

    @Column(name="Gender")
    public Boolean    gender ;

    @Column(name="Image", length=250, columnDefinition = "TEXT")
    public String     image ;


    //--- ENTITY LINKS ( RELATIONSHIP )
    @JsonIgnore
    @OneToMany(mappedBy="account", fetch = FetchType.EAGER)
    public List<Authorities> authorities ;

    @JsonIgnore
    @OneToMany(mappedBy="account")
    public List<Bookings> listOfBookings ;

    @JsonIgnore
    @OneToMany(mappedBy="account")
    public List<Paymentaccounts> listOfPaymentaccounts ;

    @JsonIgnore
    @OneToMany(mappedBy="account", fetch = FetchType.EAGER)
    private List<Shoponlines> listOfShoponlines ;

    @JsonIgnore
    @OneToMany(mappedBy="account")
    private List<Cart> listOfCart ;

    @JsonIgnore
    @OneToMany(mappedBy="account")
    private List<Discountcodes> listOfDiscountcodes ;

    @JsonIgnore
    @OneToMany(mappedBy="account")
    public List<Addressusers> listOfAddressusers ;





    //--- toString specific method
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(userid);
        sb.append("|");
        sb.append(username);
        sb.append("|");
        sb.append(fullname);
        sb.append("|");
        sb.append(password);
        sb.append("|");
        sb.append(age);
        sb.append("|");
        sb.append(email);
        sb.append("|");
        sb.append(gender);
        sb.append("|");
        sb.append(image);
        return sb.toString();
    }





	public String getUserid() {
		return userid;
	}





	public void setUserid(String userid) {
		this.userid = userid;
	}





	public String getUsername() {
		return username;
	}





	public void setUsername(String username) {
		this.username = username;
	}





	public String getFullname() {
		return fullname;
	}





	public void setFullname(String fullname) {
		this.fullname = fullname;
	}





	public String getPassword() {
		return password;
	}





	public void setPassword(String password) {
		this.password = password;
	}





	public Date getAge() {
		return age;
	}





	public void setAge(Date age) {
		this.age = age;
	}





	public String getEmail() {
		return email;
	}





	public void setEmail(String email) {
		this.email = email;
	}





	public Boolean getGender() {
		return gender;
	}





	public void setGender(Boolean gender) {
		this.gender = gender;
	}





	public String getImage() {
		return image;
	}





	public void setImage(String image) {
		this.image = image;
	}





	public List<Authorities> getAuthorities() {
		return authorities;
	}





	public void setAuthorities(List<Authorities> authorities) {
		this.authorities = authorities;
	}





	public List<Bookings> getListOfBookings() {
		return listOfBookings;
	}





	public void setListOfBookings(List<Bookings> listOfBookings) {
		this.listOfBookings = listOfBookings;
	}





	public List<Paymentaccounts> getListOfPaymentaccounts() {
		return listOfPaymentaccounts;
	}





	public void setListOfPaymentaccounts(List<Paymentaccounts> listOfPaymentaccounts) {
		this.listOfPaymentaccounts = listOfPaymentaccounts;
	}





	public List<Shoponlines> getListOfShoponlines() {
		return listOfShoponlines;
	}





	public void setListOfShoponlines(List<Shoponlines> listOfShoponlines) {
		this.listOfShoponlines = listOfShoponlines;
	}





	public List<Cart> getListOfCart() {
		return listOfCart;
	}





	public void setListOfCart(List<Cart> listOfCart) {
		this.listOfCart = listOfCart;
	}





	public List<Discountcodes> getListOfDiscountcodes() {
		return listOfDiscountcodes;
	}





	public void setListOfDiscountcodes(List<Discountcodes> listOfDiscountcodes) {
		this.listOfDiscountcodes = listOfDiscountcodes;
	}





	public List<Addressusers> getListOfAddressusers() {
		return listOfAddressusers;
	}





	public void setListOfAddressusers(List<Addressusers> listOfAddressusers) {
		this.listOfAddressusers = listOfAddressusers;
	}


	
}
