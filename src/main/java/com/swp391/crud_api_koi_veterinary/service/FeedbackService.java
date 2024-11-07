package com.swp391.crud_api_koi_veterinary.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.swp391.crud_api_koi_veterinary.model.dto.request.FeedbackRequest;
import com.swp391.crud_api_koi_veterinary.model.entity.Feedback;
import com.swp391.crud_api_koi_veterinary.repository.BookingRepository;
import com.swp391.crud_api_koi_veterinary.repository.FeedbackRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final BookingRepository bookingRepository;

    public Feedback createFeedback(FeedbackRequest request) {
        bookingRepository.findById(request.getBookingId())
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        Feedback feedback = new Feedback();
        feedback.setBookingId(bookingRepository.findById(request.getBookingId()).get());
        feedback.setRating(request.getRating());
        feedback.setFeedback(request.getFeedback());

        return feedbackRepository.save(feedback);
    }

    public Feedback getFeedbackById(int feedbackId) {
        return feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new RuntimeException("Feedback not found"));
    }

    public List<Feedback> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }

    public Feedback updateFeedback(int feedbackId, FeedbackRequest request) {
        Feedback feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new RuntimeException("Feedback not found"));

        if (request.getRating() > 0) {
            feedback.setRating(request.getRating());
        }
        if (request.getFeedback() != null && !request.getFeedback().isEmpty()) {
            feedback.setFeedback(request.getFeedback());
        }

        return feedbackRepository.save(feedback);
    }

    public void deleteFeedback(int feedbackId) {
        feedbackRepository.deleteById(feedbackId);
    }
}
