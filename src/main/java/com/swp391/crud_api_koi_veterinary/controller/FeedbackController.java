package com.swp391.crud_api_koi_veterinary.controller;

import com.swp391.crud_api_koi_veterinary.model.dto.request.FeedbackRequest;
import com.swp391.crud_api_koi_veterinary.model.entity.Feedback;
import com.swp391.crud_api_koi_veterinary.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedback")
@RequiredArgsConstructor
public class FeedbackController {
    private final FeedbackService feedbackService;

    @PostMapping
    public ResponseEntity<Feedback> createFeedback(@RequestBody FeedbackRequest request) {
        Feedback feedback = feedbackService.createFeedback(request);
        return ResponseEntity.ok(feedback);
    }

    @GetMapping("/{feedbackId}")
    public ResponseEntity<Feedback> getFeedbackById(@PathVariable int feedbackId) {
        Feedback feedback = feedbackService.getFeedbackById(feedbackId);
        return ResponseEntity.ok(feedback);
    }

    @GetMapping
    public ResponseEntity<List<Feedback>> listFeedbacks() {
        List<Feedback> feedbacks = feedbackService.getAllFeedbacks();
        return ResponseEntity.ok(feedbacks);
    }

    @PutMapping("/{feedbackId}")
    public ResponseEntity<Feedback> updateFeedback(@PathVariable int feedbackId, @RequestBody FeedbackRequest request) {
        Feedback updatedFeedback = feedbackService.updateFeedback(feedbackId, request);
        return ResponseEntity.ok(updatedFeedback);
    }

    @DeleteMapping("/{feedbackId}")
    public ResponseEntity<String> deleteFeedback(@PathVariable int feedbackId) {
        feedbackService.deleteFeedback(feedbackId);
        return ResponseEntity.ok("Feedback has been deleted");
    }
} 