package pl.mbrzozowski.vulcanizer.validation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.mbrzozowski.vulcanizer.entity.Services;
import pl.mbrzozowski.vulcanizer.enums.TypeOfServices;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

class ValidationServicesTest {

    @Test
    void validBeforeUpdate_TwoElementsTiresSwap_ThrowsIllegal() {
        List<Services> servicesList = new ArrayList<>();
        Services services = Services.builder()
                .price(10.0)
                .time(LocalTime.of(10, 0))
                .typeOfServices(TypeOfServices.TIRES_SWAP)
                .build();
        servicesList.add(services);
        servicesList.add(services);
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> ValidationServices.validBeforeUpdate(servicesList));
        Assertions.assertEquals("To much same elements", exception.getMessage());
    }

    @Test
    void validBeforeUpdate_OneElementsTiresSwap_DoesNotThrowException() {
        List<Services> servicesList = new ArrayList<>();
        Services services = Services.builder()
                .price(10.0)
                .time(LocalTime.of(10, 0))
                .typeOfServices(TypeOfServices.TIRES_SWAP)
                .build();
        servicesList.add(services);
        Assertions.assertDoesNotThrow(() -> ValidationServices.validBeforeUpdate(servicesList));
    }

    @Test
    void validBeforeUpdate_TwoElementsWheelSwap_ThrowsIllegal() {
        List<Services> servicesList = new ArrayList<>();
        Services services = Services.builder()
                .price(10.0)
                .time(LocalTime.of(10, 0))
                .typeOfServices(TypeOfServices.WHEEL_SWAP)
                .build();
        servicesList.add(services);
        servicesList.add(services);
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> ValidationServices.validBeforeUpdate(servicesList));
        Assertions.assertEquals("To much same elements", exception.getMessage());
    }

    @Test
    void validBeforeUpdate_OneElementsWheelSwap_DoesNotThrowException() {
        List<Services> servicesList = new ArrayList<>();
        Services services = Services.builder()
                .price(10.0)
                .time(LocalTime.of(10, 0))
                .typeOfServices(TypeOfServices.WHEEL_SWAP)
                .build();
        servicesList.add(services);
        Assertions.assertDoesNotThrow(() -> ValidationServices.validBeforeUpdate(servicesList));
    }

    @Test
    void validBeforeUpdate_TwoElementsWheelBalance_ThrowsIllegal() {
        List<Services> servicesList = new ArrayList<>();
        Services services = Services.builder()
                .price(10.0)
                .time(LocalTime.of(10, 0))
                .typeOfServices(TypeOfServices.WHEEL_BALANCE)
                .build();
        servicesList.add(services);
        servicesList.add(services);
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> ValidationServices.validBeforeUpdate(servicesList));
        Assertions.assertEquals("To much same elements", exception.getMessage());
    }

    @Test
    void validBeforeUpdate_OneElementsWheelBalance_DoesNotThrowException() {
        List<Services> servicesList = new ArrayList<>();
        Services services = Services.builder()
                .price(10.0)
                .time(LocalTime.of(10, 0))
                .typeOfServices(TypeOfServices.WHEEL_BALANCE)
                .build();
        servicesList.add(services);
        Assertions.assertDoesNotThrow(() -> ValidationServices.validBeforeUpdate(servicesList));
    }

    @Test
    void validBeforeUpdate_TwoElementsStraightRim_ThrowsIllegal() {
        List<Services> servicesList = new ArrayList<>();
        Services services = Services.builder()
                .price(10.0)
                .time(LocalTime.of(10, 0))
                .typeOfServices(TypeOfServices.STRAIGHTENING_RIMS)
                .build();
        servicesList.add(services);
        servicesList.add(services);
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> ValidationServices.validBeforeUpdate(servicesList));
        Assertions.assertEquals("To much same elements", exception.getMessage());
    }

    @Test
    void validBeforeUpdate_OneElementsStraightRim_DoesNotThrowException() {
        List<Services> servicesList = new ArrayList<>();
        Services services = Services.builder()
                .price(10.0)
                .time(LocalTime.of(10, 0))
                .typeOfServices(TypeOfServices.STRAIGHTENING_RIMS)
                .build();
        servicesList.add(services);
        Assertions.assertDoesNotThrow(() -> ValidationServices.validBeforeUpdate(servicesList));
    }

}