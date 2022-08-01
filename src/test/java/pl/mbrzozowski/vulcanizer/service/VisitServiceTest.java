package pl.mbrzozowski.vulcanizer.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import pl.mbrzozowski.vulcanizer.dto.VisitRequest;
import pl.mbrzozowski.vulcanizer.entity.BusinessServices;
import pl.mbrzozowski.vulcanizer.entity.User;
import pl.mbrzozowski.vulcanizer.enums.VisitStatus;
import pl.mbrzozowski.vulcanizer.repository.VisitRepository;

import java.time.LocalDateTime;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class VisitServiceTest {
    private final long ID = 1L;
    private VisitService visitService;
    private UserServiceImpl userService;
    private BusinessServicesService businessServicesService;
    private final VisitRequest visitRequest = new VisitRequest();

    @BeforeEach
    public void beforeEach() {
        VisitRepository visitRepository = mock(VisitRepository.class);
        userService = mock(UserServiceImpl.class);
        businessServicesService = mock(BusinessServicesService.class);
        visitService = new VisitService(visitRepository, userService, businessServicesService);
        visitRequest.setId(ID);
        visitRequest.setUser(ID);
        visitRequest.setService(ID);
        visitRequest.setStatus(VisitStatus.NEW_VISIT);
        visitRequest.setStartTime(LocalDateTime.now().plusDays(1).plusHours(1));
    }

    @Test
    public void save_Success() {
        User user = new User();
        when(userService.findById(ID)).thenReturn(user);
        BusinessServices businessServices = new BusinessServices();
        when(businessServicesService.findById(ID)).thenReturn(businessServices);
        Assertions.assertDoesNotThrow(() -> visitService.save(visitRequest));
    }

    @Test
    public void save_UserNull_ThrowException() {
        visitRequest.setUser(null);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> visitService.save(visitRequest));
    }

    @Test
    public void save_ServicesNull_ThrowException() {
        visitRequest.setService(null);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> visitService.save(visitRequest));
    }

    @Test
    public void save_DateBeforeNow_ThrowException() {
        visitRequest.setStartTime(LocalDateTime.now().minusMinutes(1));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> visitService.save(visitRequest));
    }

    @RepeatedTest(100)
    public void save_DateNow_ThrowException() {
        visitRequest.setStartTime(LocalDateTime.now());
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> visitService.save(visitRequest));
    }
}