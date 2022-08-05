package pl.mbrzozowski.vulcanizer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mbrzozowski.vulcanizer.dto.StateRequest;
import pl.mbrzozowski.vulcanizer.dto.StateResponse;
import pl.mbrzozowski.vulcanizer.dto.mapper.StateRequestToState;
import pl.mbrzozowski.vulcanizer.dto.mapper.StateToStateResponse;
import pl.mbrzozowski.vulcanizer.entity.State;
import pl.mbrzozowski.vulcanizer.repository.StateRepository;
import pl.mbrzozowski.vulcanizer.validation.ValidationState;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StateService {
    private final StateRepository stateRepository;

    public State save(StateRequest stateRequest) {
        State state = new StateRequestToState().apply(stateRequest);
        ValidationState.valid(state, stateRepository);
        stateRepository.save(state);
        return state;
    }

    public StateResponse update(StateRequest stateRequest) {
        State state = new StateRequestToState().apply(stateRequest);
        if (state.getId() == null) {
            throw new IllegalArgumentException("Id can not be null");
        }
        findById(state.getId());
        ValidationState.valid(state, stateRepository);
        stateRepository.save(state);
        return new StateToStateResponse().apply(state);
    }

    public List<StateResponse> findAll() {
        return stateRepository.findAll()
                .stream()
                .map(state -> new StateToStateResponse().apply(state))
                .collect(Collectors.toList());
    }

    public State findById(Long id) {
        return stateRepository
                .findById(id)
                .orElseThrow(() -> {
                    throw new IllegalArgumentException(String.format("State by id [%s] was not found", id));
                });
    }

    public void deleteById(Long id) {
    }

    public State findByName(String name) {
        return stateRepository
                .findByName(name)
                .orElseThrow(() -> {
                    throw new IllegalArgumentException(String.format("State by name [%s] was not found", name));
                });
    }

}
