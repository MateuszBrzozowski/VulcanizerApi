package pl.mbrzozowski.vulcanizer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mbrzozowski.vulcanizer.dto.VisitRequest;
import pl.mbrzozowski.vulcanizer.entity.BusinessService;
import pl.mbrzozowski.vulcanizer.entity.User;
import pl.mbrzozowski.vulcanizer.entity.Visit;
import pl.mbrzozowski.vulcanizer.exceptions.IllegalArgumentException;
import pl.mbrzozowski.vulcanizer.repository.VisitRepository;
import pl.mbrzozowski.vulcanizer.validation.ValidationVisit;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitService implements ServiceLayer<VisitRequest, Visit, Visit> {
    private final VisitRepository visitRepository;
    private final UserServiceImpl userService;
    private final BusinessServiceService serviceService;


    @Override
    public Visit save(VisitRequest visitRequest) {
        ValidationVisit.validBeforeCreated(visitRequest);
        User user = userService.findById(visitRequest.getUser());
        BusinessService businessService = serviceService.findById(visitRequest.getService());
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
}
