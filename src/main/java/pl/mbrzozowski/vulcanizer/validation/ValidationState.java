package pl.mbrzozowski.vulcanizer.validation;

import org.springframework.beans.factory.annotation.Autowired;
import pl.mbrzozowski.vulcanizer.entity.State;
import pl.mbrzozowski.vulcanizer.repository.StateRepository;

public class ValidationState {

    public static void valid(State state, StateRepository stateRepository) {
        validName(state.getName(), stateRepository);
    }

    private static void validName(String name, StateRepository stateRepository) {
        boolean isState = stateRepository.findByName(name).isPresent();
        if (name == null) {
            throw new IllegalArgumentException("State name can not be null");
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

    @Autowired
    public static void isNameExist(State state, StateRepository stateRepository) {
        if (state == null) {
            throw new IllegalArgumentException("State can not be null");
        }
        boolean isState = stateRepository.findByName(state.getName()).isPresent();
        if (state.getName() == null) {
            throw new IllegalArgumentException("State name can not be null");
        }
        if (!isState) {
            throw new IllegalArgumentException("State is not exist");
        }
    }
}
