package pl.mbrzozowski.vulcanizer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mbrzozowski.vulcanizer.dto.BusinessServicesRequest;
import pl.mbrzozowski.vulcanizer.dto.BusinessServicesResponse;
import pl.mbrzozowski.vulcanizer.service.BusinessServicesService;

@Controller
@RequiredArgsConstructor
@RestController
@RequestMapping("/services")
public class ServicesBusinessController {
    private final BusinessServicesService businessService;

    @PutMapping
    public ResponseEntity<?> update(@RequestBody BusinessServicesRequest serviceRequest) {
        BusinessServicesResponse update = businessService.update(serviceRequest);
        return new ResponseEntity<>(update, HttpStatus.OK);
    }
}
