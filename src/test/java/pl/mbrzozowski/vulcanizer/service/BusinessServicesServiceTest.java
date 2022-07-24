package pl.mbrzozowski.vulcanizer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.mbrzozowski.vulcanizer.dto.BusinessServicesRequest;
import pl.mbrzozowski.vulcanizer.entity.Business;
import pl.mbrzozowski.vulcanizer.entity.BusinessServices;
import pl.mbrzozowski.vulcanizer.repository.BusinessServicesRepository;

import java.time.LocalTime;

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

}