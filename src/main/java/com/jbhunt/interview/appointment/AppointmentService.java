package com.jbhunt.interview.appointment;

import com.jbhunt.interview.shop.ShopDTO;
import com.jbhunt.interview.shop.ShopServiceClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class AppointmentService {

    private AppointmentRepository appointmentRepository;
    private ShopServiceClient shopServiceClient;
    private AppointmentValidationUtil appointmentValidationUtil;

    public List<AppointmentDTO> getAppointmentsByCustomer(Integer customerId) {
        List<Appointment> appointmentsForCustomer = appointmentRepository.findAllByCustomerIdAndIsCompleted(customerId, false);
        List<AppointmentDTO> appointments = new ArrayList<>();
        for (Appointment appointment : appointmentsForCustomer) {
            ShopDTO shop = null;
            try {
                shop = shopServiceClient.getShopByShopId(appointment.getShopId());
            } catch (Exception e) {
                log.error("Failed to find shop");
            }
            AppointmentDTO appointmentDTO = AppointmentDTO.builder()
                    .appointmentId(appointment.getAppointmentId())
                    .customerId(appointment.getCustomerId())
                    .shopNumber(shop.getShopNumber())
                    .shopAddress(shop.getAddress())
                    .appointmentStartTime(appointment.getAppointmentStartTime())
                    .requestedServices(Arrays.asList(appointment.getRequestedService().split(",")))
                    .build();
            appointments.add(appointmentDTO);
        }
        return appointments;
    }

    public Appointment createAppointment(AppointmentDTO appointmentDTO) {
        if (appointmentValidationUtil.shopHasRequestedServices(appointmentDTO.getShopNumber(), appointmentDTO.getRequestedServices())) {
            ShopDTO shop = shopServiceClient.getShopByShopNumber(appointmentDTO.getShopNumber());
            Appointment newAppointment = Appointment.builder()
                    .customerId(appointmentDTO.getCustomerId())
                    .shopId(shop.getShopId())
                    .appointmentStartTime(appointmentDTO.getAppointmentStartTime())
                    .requestedService(String.join(",", appointmentDTO.getRequestedServices()))
                    .isCompleted(false)
                    .build();
            return appointmentRepository.save(newAppointment);
        } else {
            log.error("Services didn't match");
            return null;
        }
    }

    public void completeAppointment(Integer appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId).get();
        appointment.setIsCompleted(true);
        appointmentRepository.save(appointment);
    }
}
