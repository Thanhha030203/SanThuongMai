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
 * JPA entity class for "Typebooks"
 *
 * @author Telosys
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "Typebooks")
public class Typebooks implements Serializable {

	private static final long serialVersionUID = 1L;

	// --- ENTITY PRIMARY KEY
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer typebookid;

	// --- ENTITY DATA FIELDS
	public Integer bookid;

	// --- ENTITY LINKS ( RELATIONSHIP )
	@ManyToOne
	@JoinColumn(name = "Bookid", referencedColumnName = "BookId", insertable = false, updatable = false)
	public Books books;

	@ManyToOne
	@JoinColumn(name = "Categoryid", referencedColumnName = "CategoryId", insertable = false, updatable = false)
	public Categories categories;

	// --- toString specific method
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(typebookid);
		sb.append("|");
		sb.append("|");
		sb.append(bookid);
		return sb.toString();
	}

	public Integer getTypebookid() {
		return typebookid;
	}

	public void setTypebookid(Integer typebookid) {
		this.typebookid = typebookid;
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

	public Categories getCategories() {
		return categories;
	}

	public void setCategories(Categories categories) {
		this.categories = categories;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}