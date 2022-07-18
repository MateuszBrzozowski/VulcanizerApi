package pl.mbrzozowski.vulcanizer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mbrzozowski.vulcanizer.dto.AddressRequest;
import pl.mbrzozowski.vulcanizer.dto.AddressResponse;
import pl.mbrzozowski.vulcanizer.dto.mapper.AddressRequestToAddress;
import pl.mbrzozowski.vulcanizer.dto.mapper.AddressToAddressResponse;
import pl.mbrzozowski.vulcanizer.entity.Address;
import pl.mbrzozowski.vulcanizer.exceptions.NoSuchElementException;
import pl.mbrzozowski.vulcanizer.repository.AddressRepository;
import pl.mbrzozowski.vulcanizer.validation.ValidationAddress;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressService implements ServiceLayer<AddressRequest, AddressResponse> {
    private final AddressRepository addressRepository;
    private final StateService stateService;
    private final ValidationAddress validationAddress = new ValidationAddress();

    @Override
    public void save(AddressRequest addressRequest) {
        addressRequest.setId(null);
        Address address = new AddressRequestToAddress(stateService).apply(addressRequest);
        if (address != null) {
            validationAddress.accept(address);
            addressRepository.save(address);
        }
    }

    @Override
    public AddressResponse update(AddressRequest addressRequest) {
        findById(addressRequest.getId());
        Address address = new AddressRequestToAddress(stateService).apply(addressRequest);
        validationAddress.accept(address);
        addressRepository.save(address);
        return new AddressToAddressResponse().apply(address);
    }

    @Override
    public List<AddressResponse> findAll() {
        return addressRepository
                .findAll()
                .stream()
                .map(address -> new AddressToAddressResponse().apply(address))
                .collect(Collectors.toList());
    }

    @Override
    public AddressResponse findById(Long id) {
        Address address = addressRepository
                .findById(id)
                .orElseThrow(() -> {
                    throw new NoSuchElementException(String.format("Address by id [%s] was not found", id));
                });
        return new AddressToAddressResponse().apply(address);
    }

    @Override
    public void deleteById(Long id) {
        findById(id);
        addressRepository.deleteById(id);
    }
}
