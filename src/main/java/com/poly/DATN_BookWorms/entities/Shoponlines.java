
package com.poly.DATN_BookWorms.entities;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * JPA entity class for "Shoponlines"
 *
 * @author Telosys
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "Shoponlines")
public class Shoponlines implements Serializable {

    private static final long serialVersionUID = 1L;

    //--- ENTITY PRIMARY KEY 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer shopid;

    //--- ENTITY DATA FIELDS 
    private String shopname;

    private String phonenumber;

    @Column(name = "total")
    private Double total;

    @Column(name = "description", length = 2555)
    private String description;

    private String logo;
    private String userid;

    @Column(name = "paycount", length = 10)
    public String paycount;

    public Boolean isactive;
    




    //--- ENTITY LINKS ( RELATIONSHIP )

    @OneToMany(mappedBy = "shoponlines")
    @JsonIgnore
    public List<Books> listOfBooks;

    @OneToMany(mappedBy = "shoponlines")
    @JsonIgnore
    public List<Files> listOfFiles ; 
    
    @OneToMany(mappedBy = "shoponlines")
    @JsonIgnore
    private List<AddressShop> listOfAddressShop;

    @OneToMany(mappedBy = "shoponlines")
    @JsonIgnore
    private List<PaymentShop> listOfPaymentShop;

    
    @ManyToOne
    @JoinColumn(name="Userid", referencedColumnName="Userid", insertable=false, updatable=false)
    public Account    account ; 

    //--- toString specific method
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(shopid);
        sb.append("|");
        sb.append(shopname);
        sb.append("|");
        sb.append(phonenumber);
        sb.append("|");
        sb.append(total);
        sb.append("|");
        sb.append(description);
        sb.append(userid);
        sb.append("|");
        sb.append(paycount);
        sb.append("|");
        sb.append(isactive);
        return sb.toString();
    }
}