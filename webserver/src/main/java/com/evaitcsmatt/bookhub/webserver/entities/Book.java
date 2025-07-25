package com.evaitcsmatt.bookhub.webserver.entities;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.*;



@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="books" ,indexes = @Index(columnList = "author, genre"))
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(unique = true, nullable = false)
	@Size(min = 2, max = 150, message = "Title has to between 2 and 150 characters long!")
	private String title;
	@Column(nullable = false)
	@Size(min = 3, max = 300)
	private String author;
	@Column(nullable = false)
	private LocalDate publishDate;
	@Column(nullable = false)
	@Max(value = 500)
	private float price = 0.00f;
	@Column(nullable = false)
	@Size(min = 6, max = 30)
	private String genre;
	@Column(nullable = false)
	@Min(value = 1)
	@Max(value = 5)
	private byte rating = 1;
	@CreationTimestamp
	private LocalDateTime dateAdded;
	
	@OneToMany(mappedBy = "book", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.EAGER)
	@JsonManagedReference
	private List<Review> reviews;

	public Book(int id,
			@Size(min = 2, max = 150, message = "Title has to between 2 and 150 characters long!") String title,
			@Size(min = 3, max = 300) String author,
			LocalDate publishDate, 
			@Size(min = 6, max = 30) String genre,
			@Min(1) @Max(5) byte rating) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.publishDate = publishDate;
		this.genre = genre;
		this.rating = rating;
		this.price = 0.00f;
	}

	public Book(
			int id, 
			String title, 
			String author, 
			LocalDate publishDate, 
			String genre, 
			byte rating,
			float price) {
		this.id = id;
		this.title = title;
		this.author = author;
		this.publishDate = publishDate;
		this.genre = genre;
		this.rating = rating;
		this.price = price;
	}
	
	
}
