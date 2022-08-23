package com.jbhunt.interview.appointment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppointmentDTO {
    @JsonProperty("appointmentId")
    private Integer appointmentId;
    @JsonProperty("customerId")
    private Integer customerId;
    @JsonProperty("shopNumber")
    private String shopNumber;
    @JsonProperty("shopAddress")
    private String shopAddress;
    @JsonProperty("appointmentStartTime")
    private LocalDateTime appointmentStartTime;
    @JsonProperty("requestedServices")
    private List<String> requestedServices;
}
