package com.jbhunt.interview.appointment;

import com.jbhunt.interview.shop.ShopDTO;
import com.jbhunt.interview.shop.ShopServiceClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AppointmentValidationUtil {

    private ShopServiceClient shopServiceClient;

    public boolean shopHasRequestedServices(String shopNumber, List<String> requestedServices) {
        ShopDTO shopDTO = shopServiceClient.getShopByShopNumber(shopNumber);
        List<String> availableServices = shopDTO.getAvailableServices();
        for (String availableService : availableServices) {
            for (String requestedService : requestedServices) {
                if (requestedService.equals(availableService)) {
                    return true;
                }
            }
        }
        return false;
    }
}
