package pl.mbrzozowski.vulcanizer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mbrzozowski.vulcanizer.entity.State;
import pl.mbrzozowski.vulcanizer.repository.StateRepository;

@Service
@RequiredArgsConstructor
public class StateService {
    private final StateRepository stateRepository;

    public State findById(Long id) {
        return stateRepository
                .findById(id)
                .orElseThrow(() -> {
                    throw new IllegalArgumentException(String.format("State by id [%s] was not found", id));
                });
    }

    public State findByName(String name) {
        return stateRepository
                .findByName(name)
                .orElseThrow(() -> {
                    throw new IllegalArgumentException(String.format("State by name [%s] was not found", name));
                });
    }

}
