package pl.mbrzozowski.vulcanizer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mbrzozowski.vulcanizer.entity.State;
import pl.mbrzozowski.vulcanizer.exceptions.IllegalArgumentException;
import pl.mbrzozowski.vulcanizer.exceptions.NoSuchElementException;
import pl.mbrzozowski.vulcanizer.repository.StateRepository;
import pl.mbrzozowski.vulcanizer.validation.ValidationState;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StateService {
    private final StateRepository stateRepository;

    public void save(State state) {
        ValidationState<State> validator = new ValidationState<>(stateRepository);
        validator.accept(state);
        stateRepository.save(state);
    }

    public State findById(Long id) {
        return stateRepository
                .findById(id)
                .orElseThrow(() -> {
                    throw new NoSuchElementException(String.format("State by id [%s] was not found", id));
                });
    }

    public State findByName(String name) {
        return stateRepository
                .findByName(name)
                .orElseThrow(() -> {
                    throw new NoSuchElementException(String.format("State by name [%s] was not found", name));
                });
    }

    public List<State> findAll() {
        return stateRepository.findAll();
    }

    public State update(State state) {
        ValidationState<State> validator = new ValidationState<>(stateRepository);
        validator.accept(state);
        if (state.getId() == null) {
            throw new IllegalArgumentException("Id can not be null");
        }
        State refState = findById(state.getId());
        refState.setName(state.getName());
        stateRepository.save(refState);
        return refState;
    }
}
