package com.jbhunt.interview.appointment;

import com.jbhunt.interview.shop.ShopDTO;
import com.jbhunt.interview.shop.ShopServiceClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AppointmentValidationUtilTest {

    @InjectMocks
    private AppointmentValidationUtil appointmentValidationUtil;

    @Mock
    private ShopServiceClient shopServiceClient;

    @Test
    public void testShopHasRequestedServices() {
        // ARRANGE
        ShopDTO shopDTOA1 = ShopDTO.builder()
                .shopNumber("A1")
                .availableServices(Collections.singletonList("Service 1"))
                .build();
        when(shopServiceClient.getShopByShopNumber("A1")).thenReturn(shopDTOA1);

        // ACT
        boolean actual = appointmentValidationUtil.shopHasRequestedServices("A1", Collections.singletonList("Service 1"));

        // ASSERT
        assertTrue(actual);
    }
}
