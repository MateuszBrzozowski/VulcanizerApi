package pl.mbrzozowski.vulcanizer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.mbrzozowski.vulcanizer.dto.CompanyBranchResponse;
import pl.mbrzozowski.vulcanizer.dto.CompanyRequest;
import pl.mbrzozowski.vulcanizer.dto.CompanyResponse;
import pl.mbrzozowski.vulcanizer.entity.User;
import pl.mbrzozowski.vulcanizer.service.CompanyBranchService;
import pl.mbrzozowski.vulcanizer.service.CompanyService;
import pl.mbrzozowski.vulcanizer.util.JWTTokenAuthenticate;

import java.util.List;

import static pl.mbrzozowski.vulcanizer.constant.AppHttpHeaders.SUM_CONTROL_ID;
import static pl.mbrzozowski.vulcanizer.constant.AppHttpHeaders.SUM_CONTROL_PROPERTIES;

@Controller
@RestController
@RequestMapping("/api/v1/company/branch")
@RequiredArgsConstructor
public class CompanyBranchController {
    private final CompanyBranchService companyBranchService;
    private final CompanyService companyService;
    private final JWTTokenAuthenticate jwtTokenAuthenticate;

    @GetMapping("/waiting")
    @PreAuthorize("hasAuthority('super:admin')")
    public ResponseEntity<List<CompanyBranchResponse>> findAllCompanyBranch(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                                                            @RequestHeader(SUM_CONTROL_ID) String checkSumId,
                                                                            @RequestHeader(SUM_CONTROL_PROPERTIES) String checkSumProperties) {
        User user = jwtTokenAuthenticate.authenticate();
        jwtTokenAuthenticate.validToken(user, token, checkSumId, checkSumProperties);
        List<CompanyBranchResponse> allWaiting = companyBranchService.findAllWaiting();
        return new ResponseEntity<>(allWaiting, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<CompanyResponse> createCompanyBranch(@RequestBody CompanyRequest businessRequest,
                                                               @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                                               @RequestHeader(SUM_CONTROL_ID) String checkSumId,
                                                               @RequestHeader(SUM_CONTROL_PROPERTIES) String checkSumProperties) {
        User user = jwtTokenAuthenticate.authenticate();
        jwtTokenAuthenticate.validToken(user, token, checkSumId, checkSumProperties);
        companyService.saveForExistCompany(user, businessRequest, null);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
