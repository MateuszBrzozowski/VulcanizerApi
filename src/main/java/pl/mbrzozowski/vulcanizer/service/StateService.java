package pl.mbrzozowski.vulcanizer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mbrzozowski.vulcanizer.dto.StateRequest;
import pl.mbrzozowski.vulcanizer.dto.StateResponse;
import pl.mbrzozowski.vulcanizer.dto.mapper.StateRequestToState;
import pl.mbrzozowski.vulcanizer.dto.mapper.StateToStateResponse;
import pl.mbrzozowski.vulcanizer.entity.State;
import pl.mbrzozowski.vulcanizer.exceptions.NoSuchElementException;
import pl.mbrzozowski.vulcanizer.repository.StateRepository;
import pl.mbrzozowski.vulcanizer.validation.ValidationState;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StateService implements ServiceLayer<StateRequest, StateResponse> {
    private final StateRepository stateRepository;

    @Override
    public void save(StateRequest stateRequest) {
        State state = new StateRequestToState().apply(stateRequest);
        ValidationState validator = new ValidationState(stateRepository);
        validator.accept(state);
        stateRepository.save(state);
    }

    @Override
    public StateResponse update(StateRequest stateRequest) {
        State state = new StateRequestToState().apply(stateRequest);
        if (state.getId() == null) {
            throw new IllegalArgumentException("Id can not be null");
        }
        findById(state.getId());
        ValidationState validator = new ValidationState(stateRepository);
        validator.accept(state);
        stateRepository.save(state);
        return new StateToStateResponse().apply(state);
    }

    @Override
    public List<StateResponse> findAll() {
        return stateRepository.findAll()
                .stream()
                .map(state -> new StateToStateResponse().apply(state))
                .collect(Collectors.toList());
    }

    @Override
    public StateResponse findById(Long id) {
        State state = stateRepository
                .findById(id)
                .orElseThrow(() -> {
                    throw new NoSuchElementException(String.format("State by id [%s] was not found", id));
                });
        return new StateToStateResponse().apply(state);
    }

    @Override
    public void deleteById(Long id) {
    }

    public StateResponse findByName(String name) {
        State state = stateRepository
                .findByName(name)
                .orElseThrow(() -> {
                    throw new NoSuchElementException(String.format("State by name [%s] was not found", name));
                });
        return new StateToStateResponse().apply(state);
    }

}
