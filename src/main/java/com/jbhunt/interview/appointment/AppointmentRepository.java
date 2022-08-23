package com.jbhunt.interview.appointment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    List<Appointment> findAllByCustomerIdAndIsCompleted(Integer customerId, boolean isCompleted);
}
