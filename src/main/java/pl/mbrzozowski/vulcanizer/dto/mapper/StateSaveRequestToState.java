package pl.mbrzozowski.vulcanizer.dto.mapper;

import pl.mbrzozowski.vulcanizer.dto.StateSaveRequest;
import pl.mbrzozowski.vulcanizer.entity.State;

import java.util.function.Function;

public class StateSaveRequestToState implements Function<StateSaveRequest, State> {
    @Override
    public State apply(StateSaveRequest stateSaveRequest) {
        return State.builder()
                .name(stateSaveRequest.getName())
                .build();
    }
}
