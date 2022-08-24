package pl.mbrzozowski.vulcanizer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import pl.mbrzozowski.vulcanizer.dto.VisitRequest;
import pl.mbrzozowski.vulcanizer.entity.Visit;
import pl.mbrzozowski.vulcanizer.repository.VisitRepository;

import java.util.List;

@Service
public class VisitService {
    private final VisitRepository visitRepository;

    @Lazy
    @Autowired
    public VisitService(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    public Visit save(VisitRequest visitRequest) {
        return null;
    }

    public Visit update(VisitRequest visitRequest) {
        return null;
    }

    public List<Visit> findAll() {
        return visitRepository.findAll();
    }

    public Visit findById(Long id) {
        return visitRepository
                .findById(id)
                .orElseThrow(() -> {
                    throw new IllegalArgumentException(String.format("Not found visit by id [%s]", id));
                });
    }

    public void deleteById(Long id) {
        visitRepository.deleteById(id);
    }

}
