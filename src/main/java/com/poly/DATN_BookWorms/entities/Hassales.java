
package com.poly.DATN_BookWorms.entities;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * JPA entity class for "Hassales"
 *
 * @author Telosys
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="Hassales")
public class Hassales implements Serializable {

    private static final long serialVersionUID = 1L;

    //--- ENTITY PRIMARY KEY 
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer    hassaleid ;

    //--- ENTITY DATA FIELDS 
    private Integer    bookid ;

    private String     saleid ;

    @Temporal(TemporalType.DATE)
    private Date       starttime ;

    @Temporal(TemporalType.DATE)
    private Date       endtime ;


    //--- ENTITY LINKS ( RELATIONSHIP )
    @ManyToOne
    @JoinColumn(name="Bookid", referencedColumnName="BookId", insertable=false, updatable=false)
    private Books      books ; 

    //--- toString specific method
	@Override
    public String toString() { 
        StringBuilder sb = new StringBuilder(); 
        sb.append(hassaleid);
        sb.append("|");
        sb.append(bookid);
        sb.append("|");
        sb.append(saleid);
        sb.append("|");
        sb.append(starttime);
        sb.append("|");
        sb.append(endtime);
        return sb.toString(); 
    } 

}