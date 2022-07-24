package pl.mbrzozowski.vulcanizer.service;

import lombok.RequiredArgsConstructor;
import pl.mbrzozowski.vulcanizer.dto.ServiceRequest;
import pl.mbrzozowski.vulcanizer.entity.Business;
import pl.mbrzozowski.vulcanizer.entity.Service;
import pl.mbrzozowski.vulcanizer.exceptions.IllegalArgumentException;
import pl.mbrzozowski.vulcanizer.repository.ServiceRepository;
import pl.mbrzozowski.vulcanizer.validation.ValidationService;

import java.util.List;

@RequiredArgsConstructor
public class ServiceService implements ServiceLayer<ServiceRequest, Service, Service> {
    private final ServiceRepository serviceRepository;
    private final BusinessService businessService;

    @Override
    public Service save(ServiceRequest serviceRequest) {
        ValidationService.validBeforeCreated(serviceRequest);
        Business business = businessService.findById(serviceRequest.getBusiness());
        Service service =
                new Service(business,
                        serviceRequest.getNameOne(),
                        serviceRequest.getNameTwo(),
                        serviceRequest.getExecutionTime(),
                        serviceRequest.getPrice()
                );
        return serviceRepository.save(service);
    }

    @Override
    public Service update(ServiceRequest serviceRequest) {
        Service service = findById(serviceRequest.getId());
        ValidationService.validBeforeUpdate(serviceRequest);
        serviceToServiceNewData(service, serviceRequest);
        return serviceRepository.save(service);
    }

    private void serviceToServiceNewData(Service service, ServiceRequest serviceRequest) {
        service.setNameOne(serviceRequest.getNameOne());
        service.setNameTwo(serviceRequest.getNameTwo());
        service.setExecutionTime(serviceRequest.getExecutionTime());
        service.setPrice(serviceRequest.getPrice());
    }

    @Override
    public List<Service> findAll() {
        return serviceRepository.findAll();
    }

    @Override
    public Service findById(Long id) {
        return serviceRepository
                .findById(id)
                .orElseThrow(() -> {
                    throw new IllegalArgumentException(String.format("Not found service by id [%s]", id));
                });
    }

    @Override
    public void deleteById(Long id) {
        // TODO
        //  Nie można od tak usunąć servicu. Może ma jakas wizyte umowioną? Wizyta aktywna.
        //  Zaimplementować wizyte
        serviceRepository.deleteById(id);
    }
}
