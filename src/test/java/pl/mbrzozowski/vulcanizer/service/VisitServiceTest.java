package pl.mbrzozowski.vulcanizer.service;

import org.junit.jupiter.api.BeforeEach;
import pl.mbrzozowski.vulcanizer.dto.VisitRequest;
import pl.mbrzozowski.vulcanizer.enums.VisitStatus;
import pl.mbrzozowski.vulcanizer.repository.VisitRepository;

import java.time.LocalDateTime;

import static org.mockito.Mockito.mock;

class VisitServiceTest {
    private final long ID = 1L;
    private VisitService visitService;
    private final VisitRequest visitRequest = new VisitRequest();

    @BeforeEach
    public void beforeEach() {
        VisitRepository visitRepository = mock(VisitRepository.class);
        visitService = new VisitService(visitRepository);
        visitRequest.setId(ID);
        visitRequest.setUser(ID);
        visitRequest.setService(ID);
        visitRequest.setStatus(VisitStatus.NEW_VISIT);
        visitRequest.setStartTime(LocalDateTime.now().plusDays(1).plusHours(1));
    }

}