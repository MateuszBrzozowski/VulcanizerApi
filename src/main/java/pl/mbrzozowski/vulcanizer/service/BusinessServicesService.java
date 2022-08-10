package pl.mbrzozowski.vulcanizer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mbrzozowski.vulcanizer.dto.BusinessServicesRequest;
import pl.mbrzozowski.vulcanizer.dto.BusinessServicesResponse;
import pl.mbrzozowski.vulcanizer.dto.mapper.ServicesBusinessToServicesBusinessResponse;
import pl.mbrzozowski.vulcanizer.entity.Company;
import pl.mbrzozowski.vulcanizer.entity.BusinessServices;
import pl.mbrzozowski.vulcanizer.exceptions.EditingNotAllowedException;
import pl.mbrzozowski.vulcanizer.repository.BusinessServicesRepository;
import pl.mbrzozowski.vulcanizer.validation.ValidationService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BusinessServicesService {
    private final BusinessServicesRepository businessServicesRepository;
    private final VisitService visitService;
    private final CompanyService businessService;

    public BusinessServices save(BusinessServicesRequest serviceRequest) {
        ValidationService.validBeforeCreated(serviceRequest);
        Company business = businessService.findById(serviceRequest.getBusiness());
        BusinessServices service =
                new BusinessServices(business,
                        serviceRequest.getNameOne(),
                        serviceRequest.getNameTwo(),
                        serviceRequest.getExecutionTime(),
                        serviceRequest.getPrice()
                );
        return businessServicesRepository.save(service);
    }

    public BusinessServicesResponse update(BusinessServicesRequest serviceRequest) {
        BusinessServices service = findById(serviceRequest.getId());
        boolean activeVisit = visitService.isActiveVisit(service);
        if (activeVisit) {
            throw new EditingNotAllowedException("Can not edit becouse is active visit for this service");
        }
        ValidationService.validBeforeUpdate(serviceRequest);
        serviceToServiceNewData(service, serviceRequest);
        BusinessServices servicesBusiness = businessServicesRepository.save(service);
        return new ServicesBusinessToServicesBusinessResponse().convert(servicesBusiness);
    }

    private void serviceToServiceNewData(BusinessServices service, BusinessServicesRequest serviceRequest) {
        service.setNameOne(serviceRequest.getNameOne());
        service.setNameTwo(serviceRequest.getNameTwo());
        service.setExecutionTime(serviceRequest.getExecutionTime());
        service.setPrice(serviceRequest.getPrice());
    }

    public List<BusinessServicesResponse> findAll() {
        return businessServicesRepository
                .findAll()
                .stream()
                .map(
                        servicesBusiness ->
                                new ServicesBusinessToServicesBusinessResponse()
                                        .convert(servicesBusiness))
                .collect(Collectors.toList());
    }

    public BusinessServices findById(Long id) {
        return businessServicesRepository
                .findById(id)
                .orElseThrow(() -> {
                    throw new IllegalArgumentException(String.format("Not found service by id [%s]", id));
                });
    }

    public void deleteById(Long id) {
        businessServicesRepository.deleteById(id);
    }
}
