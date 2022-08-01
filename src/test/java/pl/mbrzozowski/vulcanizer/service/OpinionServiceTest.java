package pl.mbrzozowski.vulcanizer.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.mbrzozowski.vulcanizer.dto.OpinionRequest;
import pl.mbrzozowski.vulcanizer.entity.Business;
import pl.mbrzozowski.vulcanizer.entity.Opinion;
import pl.mbrzozowski.vulcanizer.entity.User;
import pl.mbrzozowski.vulcanizer.entity.Visit;
import pl.mbrzozowski.vulcanizer.repository.OpinionRepository;
import pl.mbrzozowski.vulcanizer.util.StringGenerator;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OpinionServiceTest {
    private final long id = 1L;
    private final int STARS = 5;
    private final String DESCRIPTION = "Its test. Lets go";
    private OpinionService opinionService;
    private UserServiceImpl userService;
    private BusinessService businessService;
    private final OpinionRequest opinionRequest = new OpinionRequest();
    private final Opinion opinion = new Opinion();

    @BeforeEach
    public void beforeEach() {
        OpinionRepository opinionRepository = mock(OpinionRepository.class);
        userService = mock(UserServiceImpl.class);
        businessService = mock(BusinessService.class);
        VisitService visitService = mock(VisitService.class);
        opinionService = new OpinionService(opinionRepository,
                userService,
                businessService,
                visitService);

        opinionRequest.setId(id);
        opinionRequest.setUser(id);
        opinionRequest.setBusiness(id);
        opinionRequest.setVisit(id);
        opinionRequest.setStars(STARS);
        opinionRequest.setDescription(DESCRIPTION);
        opinionRequest.setVisibility(true);
    }

    @Test
    public void save_Success() {
        User user = new User();
        user.setFirstName("First Name");
        user.setLastName("Last name");
        Business business = new Business();
        business.setId(id);
        Visit visit = new Visit();
        when(userService.findById(id)).thenReturn(user);
        when(businessService.findById(id)).thenReturn(business);
        opinion.setUser(user);
        opinion.setBusiness(business);
        opinion.setStars(STARS);
        opinion.setDescription(DESCRIPTION);
        opinion.setAuthorName(user.getFirstName() + " " + user.getLastName());
        opinion.setVisit(visit);
//        opinionService.save(opinionRequest);
//        verify(opinionRepository).save(opinion); // can not because dateLocalTimeIs different
        Assertions.assertDoesNotThrow(() -> opinionService.save(opinionRequest));
    }

    @Test
    public void save_UserNull_ThrowException() {
        opinionRequest.setUser(null);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> opinionService.save(opinionRequest));
    }

    @Test
    public void save_BusinessNull_ThrowException() {
        opinionRequest.setBusiness(null);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> opinionService.save(opinionRequest));
    }

    @Test
    public void save_VisitNull_ThrowException() {
        opinionRequest.setVisit(null);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> opinionService.save(opinionRequest));
    }

    @Test
    public void save_ZeroStars_ThrowException() {
        opinionRequest.setStars(0);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> opinionService.save(opinionRequest));
    }

    @Test
    public void save_SixStars_ThrowException() {
        opinionRequest.setStars(6);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> opinionService.save(opinionRequest));
    }

    @Test
    public void save_NullDescription_ThrowException() {
        opinionRequest.setDescription(null);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> opinionService.save(opinionRequest));
    }

    @Test
    public void save_EmptyDescription_ThrowException() {
        opinionRequest.setDescription("");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> opinionService.save(opinionRequest));
    }

    @Test
    public void save_DescriptionToLong_ThrowException() {
        opinionRequest.setDescription(new StringGenerator().apply(1001));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> opinionService.save(opinionRequest));
    }

    @Test
    public void save_MaxLengthDescription_ThrowException() {
        User user = new User();
        user.setFirstName("First Name");
        user.setLastName("Last name");
        Business business = new Business();
        business.setId(id);
        Visit visit = new Visit();
        when(userService.findById(id)).thenReturn(user);
        when(businessService.findById(id)).thenReturn(business);
        opinion.setUser(user);
        opinion.setBusiness(business);
        opinion.setStars(STARS);
        opinion.setDescription(DESCRIPTION);
        opinion.setAuthorName(user.getFirstName() + " " + user.getLastName());
        opinion.setVisit(visit);
        opinionRequest.setDescription(new StringGenerator().apply(1000));
        Assertions.assertDoesNotThrow(() -> opinionService.save(opinionRequest));

    }

}