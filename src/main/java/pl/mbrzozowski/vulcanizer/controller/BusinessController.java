package pl.mbrzozowski.vulcanizer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mbrzozowski.vulcanizer.dto.BusinessCreateRequest;
import pl.mbrzozowski.vulcanizer.dto.BusinessRequest;
import pl.mbrzozowski.vulcanizer.dto.BusinessResponse;
import pl.mbrzozowski.vulcanizer.service.BusinessService;

@Controller
@RestController
@RequestMapping("/business")
@RequiredArgsConstructor
public class BusinessController {
    private final BusinessService businessService;

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody BusinessCreateRequest businessCreateRequest) {
        businessService.create(businessCreateRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public ResponseEntity<BusinessResponse> update(@RequestBody BusinessRequest businessRequest) {
        return null;
    }


}
