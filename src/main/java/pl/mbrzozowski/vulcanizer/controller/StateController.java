package pl.mbrzozowski.vulcanizer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mbrzozowski.vulcanizer.dto.StateRequest;
import pl.mbrzozowski.vulcanizer.dto.StateResponse;
import pl.mbrzozowski.vulcanizer.dto.mapper.StateToStateResponse;
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
    public ResponseEntity<StateResponse> findById(@PathVariable("id") Long id) {
        StateResponse stateResponse = new StateToStateResponse().apply(stateService.findById(id));
        return new ResponseEntity<>(stateResponse, HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<StateResponse> findByName(@Param("name") String name) {
        StateResponse stateResponse = new StateToStateResponse().apply(stateService.findByName(name));
        return new ResponseEntity<>(stateResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody StateRequest state) {
        stateService.save(state);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<StateResponse> update(@RequestBody StateRequest state) {
        StateResponse update = stateService.update(state);
        return new ResponseEntity<>(update, HttpStatus.OK);
    }
}
