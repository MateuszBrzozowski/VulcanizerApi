package pl.mbrzozowski.vulcanizer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.mbrzozowski.vulcanizer.dto.BusinessRequest;
import pl.mbrzozowski.vulcanizer.dto.BusinessResponse;
import pl.mbrzozowski.vulcanizer.service.BusinessService;

@Controller
@RestController
@RequestMapping("/business")
@RequiredArgsConstructor
public class BusinessController {
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
