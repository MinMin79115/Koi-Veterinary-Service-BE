package com.swp391.crud_api_koi_veterinary.service;

import com.swp391.crud_api_koi_veterinary.model.dto.request.FeedbackRequest;
import com.swp391.crud_api_koi_veterinary.model.entity.Feedback;
import com.swp391.crud_api_koi_veterinary.repository.FeedbackRepository;
import com.swp391.crud_api_koi_veterinary.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
