package pl.mbrzozowski.vulcanizer.dto.mapper;

import pl.mbrzozowski.vulcanizer.dto.StateResponse;
import pl.mbrzozowski.vulcanizer.entity.State;

import java.util.function.Function;

public class StateToStateResponse implements Function<State, StateResponse> {

    @Override
    public StateResponse apply(State state) {
//        if (state == null) {
//            return null;
//        }
        return new StateResponse().builder()
                .id(state.getId())
                .name(state.getName())
                .build();
    }
}
