package pl.mbrzozowski.vulcanizer.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.mbrzozowski.vulcanizer.dto.BusinessServicesRequest;
import pl.mbrzozowski.vulcanizer.entity.Business;
import pl.mbrzozowski.vulcanizer.entity.BusinessServices;
import pl.mbrzozowski.vulcanizer.repository.BusinessServicesRepository;
import pl.mbrzozowski.vulcanizer.util.StringGenerator;

import java.time.LocalTime;
import java.util.Optional;

import static org.mockito.Mockito.*;

class BusinessServicesServiceTest {
    private final long id = 1L;
    private final String LINE_ONE = "Linia pierwsza";
    private final String LINE_TWO = "Linia druga";
    private final int price = 200;
    private final LocalTime executionTime = LocalTime.of(0, 30);
    private BusinessServicesService businessServicesService;
    private BusinessServicesRepository businessServicesRepository;
    private BusinessService businessService;
    private VisitService visitService;
    private final BusinessServicesRequest businessServicesRequest = new BusinessServicesRequest();

    @BeforeEach
    public void beforeEach() {
        businessService = mock(BusinessService.class);
        visitService = mock(VisitService.class);
        businessServicesRepository = mock(BusinessServicesRepository.class);
        businessServicesService =
                new BusinessServicesService(
                        businessServicesRepository,
                        visitService,
                        businessService);

        businessServicesRequest.setId(id);
        businessServicesRequest.setBusiness(id);
        businessServicesRequest.setNameOne(LINE_ONE);
        businessServicesRequest.setNameTwo(LINE_TWO);
        businessServicesRequest.setExecutionTime(executionTime);
        businessServicesRequest.setPrice(price);
    }

    @Test
    void save_Success() {
        Business business = new Business();
        business.setId(id);
        when(businessService.findById(id)).thenReturn(business);
        BusinessServices businessServices = BusinessServices.builder()
                .id(null)
                .business(business)
                .nameOne(LINE_ONE)
                .nameTwo(LINE_TWO)
                .price(price)
                .executionTime(executionTime)
                .build();
        businessServicesService.save(businessServicesRequest);
        verify(businessServicesRepository).save(businessServices);
    }

    @Test
    void save_BusinessIDNull_ThrowException() {
        Business business = new Business();
        when(businessService.findById(id)).thenThrow(IllegalArgumentException.class);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> businessServicesService.save(businessServicesRequest));
    }

    @Test
    void save_ToLongNameOne_ThrowException() {
        String toLongName = new StringGenerator().apply(256);
        businessServicesRequest.setNameOne(toLongName);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> businessServicesService.save(businessServicesRequest));
    }

    @Test
    void save_MaxLengthNameOne_DoesNotThrow() {
        String toLongName = new StringGenerator().apply(255);
        businessServicesRequest.setNameOne(toLongName);
        Assertions.assertDoesNotThrow(() -> businessServicesService.save(businessServicesRequest));
    }

    @Test
    void save_ToLongNameTwo_ThrowException() {
        String toLongName = new StringGenerator().apply(256);
        businessServicesRequest.setNameTwo(toLongName);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> businessServicesService.save(businessServicesRequest));
    }

    @Test
    void save_MaxLengthNameTwo_DoesNotThrow() {
        String toLongName = new StringGenerator().apply(255);
        businessServicesRequest.setNameTwo(toLongName);
        Assertions.assertDoesNotThrow(() -> businessServicesService.save(businessServicesRequest));
    }

    @Test
    void save_ExecutionTimeEqualsZero_ThrowException() {
        LocalTime timeZero = LocalTime.of(0, 0);
        businessServicesRequest.setExecutionTime(timeZero);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> businessServicesService.save(businessServicesRequest));
    }

    @Test
    void save_PriceOver10_000_ThrowException() {
        businessServicesRequest.setPrice(10_001);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> businessServicesService.save(businessServicesRequest));
    }

    @Test
    void save_PriceLessThanZero_ThrowException() {
        businessServicesRequest.setPrice(-1);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> businessServicesService.save(businessServicesRequest));
    }

    @Test
    void update_idNull_ThrowException() {
        businessServicesRequest.setId(null);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> businessServicesService.update(businessServicesRequest));
    }

    @Test
    void update_ToLongNameOne_ThrowException() {
        String toLongName = new StringGenerator().apply(256);
        businessServicesRequest.setNameOne(toLongName);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> businessServicesService.update(businessServicesRequest));
    }

    @Test
    void update_MaxLengthNameOne_DoesNotThrow() {
        String toLongName = new StringGenerator().apply(255);
        businessServicesRequest.setNameOne(toLongName);
        BusinessServices businessServices = new BusinessServices();
        businessServices.setId(id);
        when(businessServicesRepository.findById(id)).thenReturn(Optional.of(businessServices));
        when(businessServicesRepository.save(businessServices)).thenReturn(businessServices);
        Assertions.assertDoesNotThrow(() -> businessServicesService.update(businessServicesRequest));
    }

    @Test
    void update_ToLongNameTwo_ThrowException() {
        String toLongName = new StringGenerator().apply(256);
        businessServicesRequest.setNameTwo(toLongName);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> businessServicesService.update(businessServicesRequest));
    }

    @Test
    void update_MaxLengthNameTwo_DoesNotThrow() {
        String toLongName = new StringGenerator().apply(255);
        businessServicesRequest.setNameTwo(toLongName);
        BusinessServices businessServices = new BusinessServices();
        businessServices.setId(id);
        when(businessServicesRepository.findById(id)).thenReturn(Optional.of(businessServices));
        when(businessServicesRepository.save(businessServices)).thenReturn(businessServices);
        Assertions.assertDoesNotThrow(() -> businessServicesService.update(businessServicesRequest));
    }

    @Test
    void update_PriceOver10_000_ThrowException() {
        businessServicesRequest.setPrice(10_001);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> businessServicesService.update(businessServicesRequest));
    }

    @Test
    void update_PriceLessThanZero_ThrowException() {
        businessServicesRequest.setPrice(-1);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> businessServicesService.update(businessServicesRequest));
    }

    @Test
    void update_ExecutionTimeEqualsZero_ThrowException() {
        LocalTime timeZero = LocalTime.of(0, 0);
        businessServicesRequest.setExecutionTime(timeZero);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> businessServicesService.update(businessServicesRequest));
    }

}