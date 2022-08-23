package com.jbhunt.interview.appointment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Appointment")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer appointmentId;

    @Column(name = "shopId")
    private Integer shopId;

    @Column(name = "customerId")
    private Integer customerId;

    @Column(name = "appointmentStartTime")
    private LocalDateTime appointmentStartTime;

    @Column(name = "requestedService")
    private String requestedService;

    @Column(name = "isCompleted")
    private Boolean isCompleted;
}
