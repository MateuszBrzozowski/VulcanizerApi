package pl.mbrzozowski.vulcanizer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.mbrzozowski.vulcanizer.dto.*;
import pl.mbrzozowski.vulcanizer.entity.CustomOpeningHours;
import pl.mbrzozowski.vulcanizer.entity.OpeningHours;
import pl.mbrzozowski.vulcanizer.entity.Stand;
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
        User user = jwtTokenAuthenticate.authenticate(token, checkSumId, checkSumProperties);
        List<CompanyBranchResponse> allWaiting = companyBranchService.findAllWaiting();
        return new ResponseEntity<>(allWaiting, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<CompanyResponse> createCompanyBranch(@RequestBody CompanyRequest businessRequest,
                                                               @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                                               @RequestHeader(SUM_CONTROL_ID) String checkSumId,
                                                               @RequestHeader(SUM_CONTROL_PROPERTIES) String checkSumProperties) {
        User user = jwtTokenAuthenticate.authenticate(token, checkSumId, checkSumProperties);
        companyService.saveForExistCompany(user, businessRequest, null);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}/accept")
    @PreAuthorize("hasAuthority('super:admin')")
    public ResponseEntity<?> acceptCompanyBranch(@PathVariable("id") String companyBranchId,
                                                 @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                                 @RequestHeader(SUM_CONTROL_ID) String checkSumId,
                                                 @RequestHeader(SUM_CONTROL_PROPERTIES) String checkSumProperties) {
        User user = jwtTokenAuthenticate.authenticate(token, checkSumId, checkSumProperties);
        companyBranchService.accept(companyBranchId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}/decline")
    @PreAuthorize("hasAuthority('super:admin')")
    public ResponseEntity<?> declineCompanyBranch(@PathVariable("id") String companyBranchId,
                                                  @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                                  @RequestHeader(SUM_CONTROL_ID) String checkSumId,
                                                  @RequestHeader(SUM_CONTROL_PROPERTIES) String checkSumProperties) {
        User user = jwtTokenAuthenticate.authenticate(token, checkSumId, checkSumProperties);
        companyBranchService.decline(companyBranchId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}/stand")
    public ResponseEntity<List<Stand>> findAllStandsForBranch(@PathVariable("id") String branchId,
                                                              @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                                              @RequestHeader(SUM_CONTROL_ID) String checkSumId,
                                                              @RequestHeader(SUM_CONTROL_PROPERTIES) String checkSumProperties) {
        User user = jwtTokenAuthenticate.authenticate(token, checkSumId, checkSumProperties);
        List<Stand> stands = companyBranchService.findAllStandsForBranch(user, branchId);
        return new ResponseEntity<>(stands, HttpStatus.OK);
    }

    @PostMapping("/{id}/stand/{count}")
    public ResponseEntity<List<Stand>> standAdd(@PathVariable("id") String branchId,
                                                @PathVariable("count") String count,
                                                @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                                @RequestHeader(SUM_CONTROL_ID) String checkSumId,
                                                @RequestHeader(SUM_CONTROL_PROPERTIES) String checkSumProperties) {
        User user = jwtTokenAuthenticate.authenticate(token, checkSumId, checkSumProperties);
        List<Stand> stands = companyBranchService.addStand(user, branchId, count);
        return new ResponseEntity<>(stands, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/stand/{number}")
    public ResponseEntity<List<Stand>> standRemove(@PathVariable("id") String branchId,
                                                   @PathVariable("number") String number,
                                                   @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                                   @RequestHeader(SUM_CONTROL_ID) String checkSumId,
                                                   @RequestHeader(SUM_CONTROL_PROPERTIES) String checkSumProperties) {
        User user = jwtTokenAuthenticate.authenticate(token, checkSumId, checkSumProperties);
        List<Stand> stands = companyBranchService.removeStand(user, branchId, number);
        return new ResponseEntity<>(stands, HttpStatus.OK);
    }

    @PutMapping("/{id}/hours")
    public ResponseEntity<?> updateHoursOpening(@PathVariable("id") String branchId,
                                                @RequestBody List<OpeningHoursRequest> openingHoursRequestList,
                                                @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                                @RequestHeader(SUM_CONTROL_ID) String checkSumId,
                                                @RequestHeader(SUM_CONTROL_PROPERTIES) String checkSumProperties) {
        User user = jwtTokenAuthenticate.authenticate(token, checkSumId, checkSumProperties);
        companyBranchService.updateHoursOpening(user, branchId, openingHoursRequestList);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}/hours")
    public ResponseEntity<List<OpeningHours>> findHoursOpening(@PathVariable("id") String branchId,
                                                               @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                                               @RequestHeader(SUM_CONTROL_ID) String checkSumId,
                                                               @RequestHeader(SUM_CONTROL_PROPERTIES) String checkSumProperties) {
        User user = jwtTokenAuthenticate.authenticate(token, checkSumId, checkSumProperties);
        List<OpeningHours> openingHours = companyBranchService.findHoursOpening(user, branchId);
        return new ResponseEntity<>(openingHours, HttpStatus.OK);
    }

    @PostMapping("/{id}/hours/custom")
    public ResponseEntity<List<CustomOpeningHours>> addCustomOpeningHours(@PathVariable("id") String branchId,
                                                                          @RequestBody CustomOpeningHoursRequest openingHoursRequest,
                                                                          @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                                                          @RequestHeader(SUM_CONTROL_ID) String checkSumId,
                                                                          @RequestHeader(SUM_CONTROL_PROPERTIES) String checkSumProperties) {
        User user = jwtTokenAuthenticate.authenticate(token, checkSumId, checkSumProperties);
        List<CustomOpeningHours> customOpeningHoursList = companyBranchService.addCustomOpeningHours(user, branchId, openingHoursRequest);
        return new ResponseEntity<>(customOpeningHoursList, HttpStatus.OK);
    }

    @GetMapping("/{id}/hours/custom")
    public ResponseEntity<List<CustomOpeningHours>> findCustomOpeningHours(@PathVariable("id") String branchId,
                                                                           @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                                                           @RequestHeader(SUM_CONTROL_ID) String checkSumId,
                                                                           @RequestHeader(SUM_CONTROL_PROPERTIES) String checkSumProperties) {
        User user = jwtTokenAuthenticate.authenticate(token, checkSumId, checkSumProperties);
        List<CustomOpeningHours> customOpeningHoursList = companyBranchService.findCustomOpeningHours(user, branchId);
        return new ResponseEntity<>(customOpeningHoursList, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/hours/custom/{id_hours}")
    public ResponseEntity<List<CustomOpeningHours>> deleteCustomOpeningHours(@PathVariable("id") String branchId,
                                                                             @PathVariable("id_hours") String customOpeningHoursId,
                                                                             @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                                                             @RequestHeader(SUM_CONTROL_ID) String checkSumId,
                                                                             @RequestHeader(SUM_CONTROL_PROPERTIES) String checkSumProperties) {
        User user = jwtTokenAuthenticate.authenticate(token, checkSumId, checkSumProperties);
        List<CustomOpeningHours> customOpeningHoursList = companyBranchService.deleteCustomOpeningHours(user, branchId, customOpeningHoursId);
        return new ResponseEntity<>(customOpeningHoursList, HttpStatus.OK);
    }

    @PutMapping("/{id}/services")
    public ResponseEntity<?> updateServices(@PathVariable("id") String branchId,
                                            @RequestBody List<ServicesRequest> servicesRequestList,
                                            @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                            @RequestHeader(SUM_CONTROL_ID) String checkSumId,
                                            @RequestHeader(SUM_CONTROL_PROPERTIES) String checkSumProperties){
        User user = jwtTokenAuthenticate.authenticate(token, checkSumId, checkSumProperties);
        companyBranchService.updateServices(user, branchId, servicesRequestList);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
