package pl.mbrzozowski.vulcanizer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mbrzozowski.vulcanizer.dto.OpinionRequest;
import pl.mbrzozowski.vulcanizer.dto.OpinionResponse;
import pl.mbrzozowski.vulcanizer.dto.mapper.OpinionToOpinionResponse;
import pl.mbrzozowski.vulcanizer.entity.Business;
import pl.mbrzozowski.vulcanizer.entity.Opinion;
import pl.mbrzozowski.vulcanizer.exceptions.IllegalArgumentException;
import pl.mbrzozowski.vulcanizer.repository.OpinionRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OpinionService implements ServiceLayer<OpinionRequest, OpinionResponse, Opinion> {
    private final OpinionRepository opinionRepository;

    @Override
    public Opinion save(OpinionRequest opinionRequest) {
        return null;
    }

    @Override
    public OpinionResponse update(OpinionRequest opinionRequest) {
        return null;
    }

    @Override
    public List<OpinionResponse> findAll() {
        List<Opinion> all = opinionRepository.findAll();
        return all
                .stream()
                .map(opinion -> new OpinionToOpinionResponse().convert(opinion))
                .collect(Collectors.toList());
    }

    @Override
    public Opinion findById(Long id) {
        return opinionRepository.findById(id)
                .orElseThrow(() -> {
                    throw new IllegalArgumentException(String.format("Not found opinion by id [%s]", id));
                });
    }

    @Override
    public void deleteById(Long id) {
        opinionRepository.deleteById(id);
    }

    public List<OpinionResponse> findByBusiness(Business business) {
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
