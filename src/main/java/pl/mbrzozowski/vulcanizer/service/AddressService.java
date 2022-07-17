package pl.mbrzozowski.vulcanizer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mbrzozowski.vulcanizer.dto.AddressRequest;
import pl.mbrzozowski.vulcanizer.dto.AddressResponse;
import pl.mbrzozowski.vulcanizer.dto.mapper.AddressRequestToAddress;
import pl.mbrzozowski.vulcanizer.entity.Address;
import pl.mbrzozowski.vulcanizer.repository.AddressRepository;
import pl.mbrzozowski.vulcanizer.validation.ValidationAddress;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService implements ServiceLayer<AddressRequest, AddressResponse> {
    private final AddressRepository addressRepository;
    private final StateService stateService;

    @Override
    public void save(AddressRequest addressRequest) {
        ValidationAddress validationAddress = new ValidationAddress();
        Address address = new AddressRequestToAddress(stateService).apply(addressRequest);
        validationAddress.accept(address);
        addressRepository.save(address);
    }

    @Override
    public AddressResponse update(AddressRequest addressRequest) {
        return null;
    }

    @Override
    public List<AddressResponse> findAll() {
        return null;
    }

    @Override
    public AddressResponse findById(Long t) {
        return null;
    }

    @Override
    public void delete(AddressRequest addressRequest) {

    }
}
