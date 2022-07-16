package pl.mbrzozowski.vulcanizer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mbrzozowski.vulcanizer.entity.State;
import pl.mbrzozowski.vulcanizer.exceptions.StateWasNotFoundException;
import pl.mbrzozowski.vulcanizer.repository.StateRepository;

@Service
@RequiredArgsConstructor
public class StateService {
    private final StateRepository stateRepository;

    public void save(State state) {
        stateRepository.save(state);
    }

    public void deleteById(State state) {
        stateRepository.deleteById(state.getId());
    }

    public State findById(Long id) {
        return stateRepository
                .findById(id)
                .orElseThrow(() -> {
                    throw new StateWasNotFoundException(String.format("State by id [%s] was not found", id));
                });
    }

    public State findByName(String name) {
        return stateRepository
                .findByName(name)
                .orElseThrow(() -> {
                    throw new StateWasNotFoundException(String.format("State by name [%s] was not found", name));
                });
    }

    public State update(State state) {
        State refState = findById(state.getId());
        refState.setName(state.getName());
        stateRepository.save(refState);
        return refState;
    }
}
