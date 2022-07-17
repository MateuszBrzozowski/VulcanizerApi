package pl.mbrzozowski.vulcanizer.validation;

import pl.mbrzozowski.vulcanizer.entity.State;
import pl.mbrzozowski.vulcanizer.exceptions.IllegalArgumentException;
import pl.mbrzozowski.vulcanizer.exceptions.NullPointerException;
import pl.mbrzozowski.vulcanizer.repository.StateRepository;

import java.util.function.Consumer;

@SuppressWarnings("rawtypes")
public class ValidationState implements Consumer<State> {
    private final StateRepository stateRepository;

    public ValidationState(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    @Override
    public void accept(State state) {
        validName(state.getName());
    }

    private void validName(String name) {
        boolean isState = stateRepository.findByName(name).isPresent();
        if (name == null) {
            throw new NullPointerException("State name can not be null");
        }
        if (name.equalsIgnoreCase("")) {
            throw new IllegalArgumentException("State name can not be empty");
        }
        if (name.length() > 50) {
            throw new IllegalArgumentException("State name to long");
        }
        if (isState) {
            throw new IllegalArgumentException(
                    String.format("State %s is exist", name.toUpperCase()));
        }
    }
}
