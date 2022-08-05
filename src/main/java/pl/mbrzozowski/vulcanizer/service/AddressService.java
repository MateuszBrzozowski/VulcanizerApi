package pl.mbrzozowski.vulcanizer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mbrzozowski.vulcanizer.dto.AddressRequest;
import pl.mbrzozowski.vulcanizer.dto.AddressResponse;
import pl.mbrzozowski.vulcanizer.dto.mapper.AddressRequestToAddress;
import pl.mbrzozowski.vulcanizer.dto.mapper.AddressToAddressResponse;
import pl.mbrzozowski.vulcanizer.entity.Address;
import pl.mbrzozowski.vulcanizer.repository.AddressRepository;
import pl.mbrzozowski.vulcanizer.validation.ValidationAddress;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;
    private final StateService stateService;

    public Address save(AddressRequest addressRequest) {
        addressRequest.setId(null);
        Address address = new AddressRequestToAddress(stateService).apply(addressRequest);
        ValidationAddress.valid(address);
        return addressRepository.save(address);
    }

    public AddressResponse update(AddressRequest addressRequest) {
        Address address = new AddressRequestToAddress(stateService).apply(addressRequest);
        if (addressRequest != null) {
            findById(address.getId());
            ValidationAddress.valid(address);
            if (address.getState() == null) {
                deleteStateFromAddress(address.getId());
            }
            addressRepository.save(address);
            return new AddressToAddressResponse().apply(address);
        }
        return null;
    }

    private void deleteStateFromAddress(Long addressId) {
        addressRepository.deleteStateByAddressId(addressId);
    }

    public List<AddressResponse> findAll() {
        return addressRepository
                .findAll()
                .stream()
                .map(address -> new AddressToAddressResponse().apply(address))
                .collect(Collectors.toList());
    }

    public Address findById(Long id) {
        return addressRepository
                .findById(id)
                .orElseThrow(() -> {
                    throw new IllegalArgumentException(String.format("Address by id [%s] was not found", id));
                });
    }

    public void deleteById(Long id) {
        findById(id);
        addressRepository.deleteById(id);
    }
}
