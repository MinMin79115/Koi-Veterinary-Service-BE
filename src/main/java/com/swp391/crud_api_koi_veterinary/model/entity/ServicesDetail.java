package com.swp391.crud_api_koi_veterinary.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "services_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicesDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "services_detail_id")
    private int servicesDetailId;

    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private Services serviceId;

    @ManyToOne
    @JoinColumn(name = "service_type_id", nullable = false)
    private ServicesType serviceTypeId;
}
