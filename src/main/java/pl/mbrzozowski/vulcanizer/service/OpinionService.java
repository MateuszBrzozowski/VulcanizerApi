package pl.mbrzozowski.vulcanizer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mbrzozowski.vulcanizer.dto.OpinionRequest;
import pl.mbrzozowski.vulcanizer.dto.OpinionResponse;
import pl.mbrzozowski.vulcanizer.dto.mapper.OpinionToOpinionResponse;
import pl.mbrzozowski.vulcanizer.entity.Company;
import pl.mbrzozowski.vulcanizer.entity.Opinion;
import pl.mbrzozowski.vulcanizer.entity.User;
import pl.mbrzozowski.vulcanizer.repository.OpinionRepository;
import pl.mbrzozowski.vulcanizer.validation.ValidationOpinion;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OpinionService {
    private final OpinionRepository opinionRepository;
    private final UserServiceImpl userService;
    private final CompanyService businessService;
    private final VisitService visitService;

    public Opinion save(OpinionRequest opinionRequest) {
        ValidationOpinion.validBeforeCreated(opinionRequest);
        User user = userService.findById(opinionRequest.getUser());
        Company business = businessService.findById(opinionRequest.getBusiness());
        Opinion opinion = new Opinion(user,
                business,
                opinionRequest.getStars(),
                opinionRequest.getDescription());
        Opinion savedOpinion = opinionRepository.save(opinion);
        visitService.addOpinionToVisit(opinionRequest.getVisit(), savedOpinion);
        return savedOpinion;
    }

    public OpinionResponse update(OpinionRequest opinionRequest) {
        Opinion opinion = findById(opinionRequest.getId());
        ValidationOpinion.validBeforeEdit(opinionRequest);
        opinion.setDescription(opinionRequest.getDescription());
        opinion.setStars(opinionRequest.getStars());
        Opinion save = opinionRepository.save(opinion);
        return new OpinionToOpinionResponse().convert(save);
    }

    public List<OpinionResponse> findAll() {
        List<Opinion> all = opinionRepository.findAll();
        return all
                .stream()
                .map(opinion -> new OpinionToOpinionResponse().convert(opinion))
                .collect(Collectors.toList());
    }

    public Opinion findById(Long id) {
        return opinionRepository.findById(id)
                .orElseThrow(() -> {
                    throw new IllegalArgumentException(String.format("Not found opinion by id [%s]", id));
                });
    }

    public void deleteById(Long id) {
        opinionRepository.deleteById(id);
    }

    public List<OpinionResponse> findByBusiness(Company business) {
        List<Opinion> opinions = opinionRepository
                .findByBusiness(business)
                .stream()
                .toList();
        return opinions
                .stream()
                .map(opinion -> new OpinionToOpinionResponse().convert(opinion))
                .collect(Collectors.toList());
    }
}
