package pl.mbrzozowski.vulcanizer.dto.mapper;

import pl.mbrzozowski.vulcanizer.dto.StateResponse;
import pl.mbrzozowski.vulcanizer.entity.State;

import java.util.function.Function;

public class StateToStateResponse implements Function<State, StateResponse> {

    @Override
    public StateResponse apply(State state) {
        return new StateResponse(state.getName());
    }
}
