package pl.mbrzozowski.vulcanizer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.mbrzozowski.vulcanizer.dto.PublicHolidaysRequest;
import pl.mbrzozowski.vulcanizer.entity.PublicHolidays;
import pl.mbrzozowski.vulcanizer.service.PublicHolidaysService;
import pl.mbrzozowski.vulcanizer.util.JWTTokenAuthenticate;

import java.util.List;

import static pl.mbrzozowski.vulcanizer.constant.AppHttpHeaders.SUM_CONTROL_ID;
import static pl.mbrzozowski.vulcanizer.constant.AppHttpHeaders.SUM_CONTROL_PROPERTIES;

@Controller
@RestController
@RequestMapping("/api/v1/public_holidays")
@RequiredArgsConstructor
public class PublicHolidaysController {
    private final PublicHolidaysService publicHolidaysService;
    private final JWTTokenAuthenticate jwtTokenAuthenticate;

    @PostMapping
    @PreAuthorize("hasAuthority('super:admin')")
    public ResponseEntity<?> add(@RequestBody PublicHolidaysRequest publicHolidaysRequest,
                                 @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                 @RequestHeader(SUM_CONTROL_ID) String checkSumId,
                                 @RequestHeader(SUM_CONTROL_PROPERTIES) String checkSumProperties) {
        jwtTokenAuthenticate.authenticate(token, checkSumId, checkSumProperties);
        publicHolidaysService.save(publicHolidaysRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('super:admin')")
    public ResponseEntity<?> delete(@PathVariable String id,
                                 @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                 @RequestHeader(SUM_CONTROL_ID) String checkSumId,
                                 @RequestHeader(SUM_CONTROL_PROPERTIES) String checkSumProperties) {
        jwtTokenAuthenticate.authenticate(token, checkSumId, checkSumProperties);
        publicHolidaysService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/currentYear")
    @PreAuthorize("hasAuthority('super:admin')")
    public ResponseEntity<List<PublicHolidays>> findAllThisYear(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                                                @RequestHeader(SUM_CONTROL_ID) String checkSumId,
                                                                @RequestHeader(SUM_CONTROL_PROPERTIES) String checkSumProperties) {
        jwtTokenAuthenticate.authenticate(token, checkSumId, checkSumProperties);
        List<PublicHolidays> holidaysList = publicHolidaysService.findAllThisYear();
        return new ResponseEntity<>(holidaysList, HttpStatus.OK);
    }

    @GetMapping("/nextYear")
    @PreAuthorize("hasAuthority('super:admin')")
    public ResponseEntity<List<PublicHolidays>> findAllNextYear(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                                                @RequestHeader(SUM_CONTROL_ID) String checkSumId,
                                                                @RequestHeader(SUM_CONTROL_PROPERTIES) String checkSumProperties) {
        jwtTokenAuthenticate.authenticate(token, checkSumId, checkSumProperties);
        List<PublicHolidays> holidaysList = publicHolidaysService.findAllNextYear();
        return new ResponseEntity<>(holidaysList, HttpStatus.OK);
    }

    @GetMapping("/nextMonths")
    public ResponseEntity<List<PublicHolidays>> findAllNextTwoMonths(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                                                @RequestHeader(SUM_CONTROL_ID) String checkSumId,
                                                                @RequestHeader(SUM_CONTROL_PROPERTIES) String checkSumProperties) {
        jwtTokenAuthenticate.authenticate(token, checkSumId, checkSumProperties);
        List<PublicHolidays> holidaysList = publicHolidaysService.findNextTwoMonths();
        return new ResponseEntity<>(holidaysList, HttpStatus.OK);
    }

}
