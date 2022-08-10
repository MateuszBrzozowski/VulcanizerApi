package pl.mbrzozowski.vulcanizer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mbrzozowski.vulcanizer.dto.BusinessPublicResponse;
import pl.mbrzozowski.vulcanizer.exceptions.ExceptionHandling;
import pl.mbrzozowski.vulcanizer.service.CompanyService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/public-data") //public path
public class PublicResourcesController extends ExceptionHandling {
    private final CompanyService businessService;

    @GetMapping("/business/recommend")
    public ResponseEntity<List<BusinessPublicResponse>> getRecommendBusinesses(){
        List<BusinessPublicResponse> recommendBusiness = businessService.getRecommendBusiness();
        return new ResponseEntity<>(recommendBusiness, HttpStatus.OK);
    }
}
