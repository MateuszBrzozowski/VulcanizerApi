package pl.mbrzozowski.vulcanizer.dto.mapper;

import pl.mbrzozowski.vulcanizer.dto.StateRequest;
import pl.mbrzozowski.vulcanizer.entity.State;

import java.util.function.Function;

public class StateRequestToState implements Function<StateRequest, State> {
    @Override
    public State apply(StateRequest stateRequest) {
        return State.builder()
                .id(stateRequest.getId())
                .name(stateRequest.getName())
                .build();
    }
}
