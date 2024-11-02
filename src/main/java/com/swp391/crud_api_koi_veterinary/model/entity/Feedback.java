package com.swp391.crud_api_koi_veterinary.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "feedback")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedback_id")
    private int feedbackId;

    @OneToOne
    @JoinColumn(name = "booking_id")
    private Booking bookingId;

    @Column(name = "rating")
    private int rating;

    @Column(name = "feedback")
    private String feedback;

}
