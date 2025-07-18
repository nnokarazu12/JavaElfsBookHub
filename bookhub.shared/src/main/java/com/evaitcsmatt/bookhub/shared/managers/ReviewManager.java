package com.evaitcsmatt.bookhub.shared.managers;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.evaitcsmatt.bookhub.shared.entities.Book;
import com.evaitcsmatt.bookhub.shared.entities.Review;
import com.evaitcsmatt.bookhub.shared.exceptions.ReviewInputException;
import com.evaitcsmatt.bookhub.shared.repository.ReviewRepository;

public class ReviewManager {
	
	@Autowired
	private ReviewRepository repository;
	
	public static List<Review> getAllBookReviews(Book book){
		return book.getReviews();
	}
	
	public static Review getBookReviewById(Book book, int reviewId) {
		for (Review review : book.getReviews()) {
			if(review.getReviewId() == reviewId) {
				return review;
			}
		}
		
		return null;
	}
	
	public static void addReview(Book book, String username, String comment, byte rating) {
		if(comment.isBlank()) throw new ReviewInputException("Comment cannot be blank for a review");
		if(rating < 0 || rating > 5) throw new ReviewInputException("Rating cannot execeed 5 or go below 0");
		Review review = new Review(username, comment, rating);
		book.getReviews().add(review);
		System.out.println("Review successfully added!");
	}
	
	public static boolean updateReview(Book book, int reviewId, String comment, byte rating) {
		boolean results = false;
		Review review = getBookReviewById(book, reviewId);
		if(!review.getComment().equals(comment) || !comment.isBlank()) {
			review.setComment(comment);
			results = true;
		}
		if(review.getRating() != rating && !(rating < 0 || rating > 5)) {
			review.setRating(rating);
			results = true;
		}
		return results;
	}
	
	public static boolean deleteReviewById(Book book, int reviewId) {
		return book.getReviews().removeIf(b -> b.getReviewId() == reviewId);
	}
}
