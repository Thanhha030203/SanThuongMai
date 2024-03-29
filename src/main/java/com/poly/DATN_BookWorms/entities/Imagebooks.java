/*
 * Generated by Telosys ( http://www.telosys.org/ ) version 3.3.0
 */
package com.poly.DATN_BookWorms.entities;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * JPA entity class for "Imagebooks"
 *
 * @author Telosys
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="Imagebooks")
public class Imagebooks implements Serializable {

    public static final long serialVersionUID = 1L;

    //--- ENTITY PRIMARY KEY 
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Integer    filedid ;

    //--- ENTITY DATA FIELDS 

    public String     name ;

    public String     typefile ;

    public Integer    bookid ;


    //--- ENTITY LINKS ( RELATIONSHIP )
    @ManyToOne
    @JoinColumn(name="Bookid", referencedColumnName="BookId", insertable=false, updatable=false)
    public Books      books ;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(); 
        sb.append("|");
        sb.append(name);
        sb.append("|");
        sb.append(typefile);
        sb.append("|");
        sb.append(bookid);
        return sb.toString(); 
    }

	public Integer getFiledid() {
		return filedid;
	}

	public void setFiledid(Integer filedid) {
		this.filedid = filedid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTypefile() {
		return typefile;
	}

	public void setTypefile(String typefile) {
		this.typefile = typefile;
	}

	public Integer getBookid() {
		return bookid;
	}

	public void setBookid(Integer bookid) {
		this.bookid = bookid;
	}

	public Books getBooks() {
		return books;
	}

	public void setBooks(Books books) {
		this.books = books;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}



}