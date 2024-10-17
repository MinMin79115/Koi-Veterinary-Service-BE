package com.swp391.crud_api_koi_veterinary.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "veterinarian")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Veterinarian {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "veterinarian_id")
    private int veterinarianId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserAccount user;

    @ManyToOne
    @JoinColumn(name = "service_type_id", nullable = true)
    private ServicesType serviceTypeId;
}
