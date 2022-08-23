package com.jbhunt.interview.appointment;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class AppointmentController {

    private AppointmentService appointmentService;

    @GetMapping("/customers/{customerId}/appointments")
    public ResponseEntity getAppointmentsByCustomer(@PathVariable Integer customerId) {
        List<AppointmentDTO> appointments = appointmentService.getAppointmentsByCustomer(customerId);
        return ResponseEntity.ok(appointments);
    }

    @PostMapping("/appointments")
    public ResponseEntity createAppointment(@RequestBody AppointmentDTO appointmentDTO) {
        Appointment createdAppointment = appointmentService.createAppointment(appointmentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAppointment);
    }

    @PatchMapping("/appointments/{appointmentId}/complete")
    public ResponseEntity completeAppointment(@PathVariable Integer appointmentId) {
        appointmentService.completeAppointment(appointmentId);
        return ResponseEntity.noContent().build();
    }
}
