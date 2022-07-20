package pl.mbrzozowski.vulcanizer.dto.mapper;

import pl.mbrzozowski.vulcanizer.dto.StateResponse;
import pl.mbrzozowski.vulcanizer.entity.State;

import java.util.function.Function;

public class StateResponseToState implements Function<StateResponse, State> {
    @Override
    public State apply(StateResponse stateResponse) {
//        if (stateResponse == null) {
//            return null;
//        }
        return State.builder()
                .id(stateResponse.getId())
                .name(stateResponse.getName())
                .build();
    }
}
