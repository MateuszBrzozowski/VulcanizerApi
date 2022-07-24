package pl.mbrzozowski.vulcanizer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import pl.mbrzozowski.vulcanizer.dto.VisitRequest;
import pl.mbrzozowski.vulcanizer.entity.BusinessServices;
import pl.mbrzozowski.vulcanizer.entity.Opinion;
import pl.mbrzozowski.vulcanizer.entity.User;
import pl.mbrzozowski.vulcanizer.entity.Visit;
import pl.mbrzozowski.vulcanizer.enums.VisitStatus;
import pl.mbrzozowski.vulcanizer.exceptions.IllegalArgumentException;
import pl.mbrzozowski.vulcanizer.repository.VisitRepository;
import pl.mbrzozowski.vulcanizer.validation.ValidationVisit;

import java.util.List;

@Service
public class VisitService implements ServiceLayer<VisitRequest, Visit, Visit> {
    private final VisitRepository visitRepository;
    private final UserServiceImpl userService;
    private final BusinessServicesService serviceService;

    @Lazy
    @Autowired
    public VisitService(VisitRepository visitRepository,
                        UserServiceImpl userService,
                        BusinessServicesService serviceService) {
        this.visitRepository = visitRepository;
        this.userService = userService;
        this.serviceService = serviceService;
    }

    @Override
    public Visit save(VisitRequest visitRequest) {
        ValidationVisit.validBeforeCreated(visitRequest);
        User user = userService.findById(visitRequest.getUser());
        BusinessServices businessService = serviceService.findById(visitRequest.getService());
        Visit visit = new Visit(user, businessService, visitRequest.getStartTime());
        return visitRepository.save(visit);
    }

    @Override
    public Visit update(VisitRequest visitRequest) {
        Visit visit = findById(visitRequest.getId());
        ValidationVisit.validBeforeEditing(visitRequest);
        VisitToVisitNewData(visit, visitRequest);
        return visitRepository.save(visit);
    }

    private void VisitToVisitNewData(Visit visit, VisitRequest visitRequest) {
        visit.setStatus(visitRequest.getStatus());
        visit.setStartTime(visitRequest.getStartTime());
    }

    @Override
    public List<Visit> findAll() {
        return visitRepository.findAll();
    }

    @Override
    public Visit findById(Long id) {
        return visitRepository
                .findById(id)
                .orElseThrow(() -> {
                    throw new IllegalArgumentException(String.format("Not found visit by id [%s]", id));
                });
    }

    @Override
    public void deleteById(Long id) {
        visitRepository.deleteById(id);
    }

    public List<Visit> findByService(BusinessServices servicesBusiness) {
        return visitRepository.findByService(servicesBusiness).stream().toList();
    }

    private List<Visit> findByUser(User user) {
        return visitRepository.findByUser(user).stream().toList();
    }

    /**
     * @param userId Long
     * @return All visits for user with id
     */
    public List<Visit> findAllByUserId(Long userId) {
        User user = userService.findById(userId);
        return findByUser(user);
    }

    /**
     * @param userId Long
     * @return Last user visit if is no opinion else return null.
     */
    public Visit findLastVisitWithoutOpinionForUser(Long userId) {
        User user = userService.findById(userId);
        List<Visit> visits = findByUser(user);
        List<Visit> endedVisits =
                visits.stream()
                        .filter(
                                visit -> visit.getStatus().equals(VisitStatus.ENDED))
                        .toList();
        Visit lastVisit = null;
        for (Visit v : endedVisits) {
            if (lastVisit == null) {
                lastVisit = v;
            }
            if (lastVisit.getStartTime().isBefore(v.getStartTime())) {
                lastVisit = v;
            }
        }
        if (lastVisit != null) {
            if (lastVisit.getOpinion() == null) {
                return lastVisit;
            }
        }
        return null;
    }

    /**
     * @param businessServices
     * @return true if business service have active or waiting visits, else return false.
     */
    public boolean isActiveVisit(BusinessServices businessServices) {
        List<Visit> visits = findByService(businessServices);
        for (Visit v : visits) {
            if (v.getStatus().equals(VisitStatus.NEW_VISIT) ||
                    v.getStatus().equals(VisitStatus.CONFIRM) ||
                    v.getStatus().equals(VisitStatus.REQ_CONFIRM_BY_USER)) {
                return true;
            }
        }
        return false;
    }

    public void addOpinionToVisit(Long visitId, Opinion savedOpinion) {
        Visit visit = findById(visitId);
        visit.setOpinion(savedOpinion);
        visitRepository.save(visit);
    }
}
