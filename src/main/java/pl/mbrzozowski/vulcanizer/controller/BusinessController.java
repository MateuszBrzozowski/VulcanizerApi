package pl.mbrzozowski.vulcanizer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.mbrzozowski.vulcanizer.dto.BusinessPublicResponse;
import pl.mbrzozowski.vulcanizer.dto.BusinessRequest;
import pl.mbrzozowski.vulcanizer.dto.BusinessResponse;
import pl.mbrzozowski.vulcanizer.exceptions.ExceptionHandling;
import pl.mbrzozowski.vulcanizer.exceptions.IllegalArgumentException;
import pl.mbrzozowski.vulcanizer.exceptions.UserNotFoundException;
import pl.mbrzozowski.vulcanizer.service.BusinessService;

import java.util.List;

@Controller
@RestController
@RequestMapping("/api/v1/business")
@RequiredArgsConstructor
public class BusinessController extends ExceptionHandling {
    private final BusinessService businessService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody BusinessRequest businessRequest) {
        businessService.save(businessRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<BusinessResponse> update(@RequestBody BusinessRequest businessRequest) {
        BusinessResponse businessResponse = businessService.update(businessRequest);
        return new ResponseEntity<>(businessResponse,HttpStatus.OK);
    }
}
