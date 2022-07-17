package pl.mbrzozowski.vulcanizer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mbrzozowski.vulcanizer.dto.StateResponse;
import pl.mbrzozowski.vulcanizer.entity.State;
import pl.mbrzozowski.vulcanizer.service.StateService;

import java.util.List;

@RestController
@RequestMapping("/address/state")
public class StateController {
    private final StateService stateService;

    @Autowired
    public StateController(StateService stateService) {
        this.stateService = stateService;
    }

    @GetMapping
    public ResponseEntity<List<StateResponse>> findAll() {
        List<StateResponse> states = stateService.findAll();
        return new ResponseEntity<>(states, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<State> findById(@PathVariable("id") Long id) {
        State state = stateService.findById(id);
        return new ResponseEntity<>(state, HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<State> findByName(@Param("name") String name) {
        State state = stateService.findByName(name);
        return new ResponseEntity<>(state, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody State state) {
        stateService.save(state);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<State> update(@RequestBody State state) {
        State update = stateService.update(state);
        return new ResponseEntity<>(update, HttpStatus.OK);
    }
}
