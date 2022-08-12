package pl.mbrzozowski.vulcanizer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.mbrzozowski.vulcanizer.dto.CompanyResponse;
import pl.mbrzozowski.vulcanizer.dto.CompanyRequest;
import pl.mbrzozowski.vulcanizer.entity.Company;
import pl.mbrzozowski.vulcanizer.entity.User;
import pl.mbrzozowski.vulcanizer.exceptions.ExceptionHandling;
import pl.mbrzozowski.vulcanizer.service.CompanyService;
import pl.mbrzozowski.vulcanizer.util.JWTTokenAuthenticate;

import java.util.List;

import static pl.mbrzozowski.vulcanizer.constant.AppHttpHeaders.SUM_CONTROL_ID;
import static pl.mbrzozowski.vulcanizer.constant.AppHttpHeaders.SUM_CONTROL_PROPERTIES;

@Controller
@RestController
@RequestMapping("/api/v1/company")
@RequiredArgsConstructor
public class CompanyController extends ExceptionHandling {
    private final CompanyService companyService;
    private final JWTTokenAuthenticate jwtTokenAuthenticate;

    @PostMapping("/create")
    public ResponseEntity<CompanyResponse> create(@RequestBody CompanyRequest businessRequest,
                                                  @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                                  @RequestHeader(SUM_CONTROL_ID) String checkSumId,
                                                  @RequestHeader(SUM_CONTROL_PROPERTIES) String checkSumProperties) {
        User user = jwtTokenAuthenticate.authenticate();
        jwtTokenAuthenticate.validToken(user, token, checkSumId, checkSumProperties);
        companyService.save(user, businessRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<Company>> findAll() {
        List<Company> businessList = companyService.findAll();
        return new ResponseEntity<>(businessList, HttpStatus.OK);
    }

//    @PutMapping
//    public ResponseEntity<BusinessResponse> update(@RequestBody BusinessRequest businessRequest) {
//        BusinessResponse businessResponse = businessService.update(businessRequest);
//        return new ResponseEntity<>(businessResponse, HttpStatus.OK);
//    }
}
