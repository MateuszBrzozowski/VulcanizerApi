package pl.mbrzozowski.vulcanizer.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import pl.mbrzozowski.vulcanizer.dto.AddressRequest;
import pl.mbrzozowski.vulcanizer.dto.AddressResponse;
import pl.mbrzozowski.vulcanizer.dto.mapper.AddressToAddressResponse;
import pl.mbrzozowski.vulcanizer.entity.Address;
import pl.mbrzozowski.vulcanizer.entity.State;
import pl.mbrzozowski.vulcanizer.repository.AddressRepository;
import pl.mbrzozowski.vulcanizer.validation.ValidationAddress;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;
    private final StateService stateService;

    /**
     * Validate fields and saving new address to DB for user. All fields can not be blank.
     *
     * @param addressRequest which save
     * @return {@link Address} which saved in DB or <b>NULL</b> when all fields are blank.
     * @throws IllegalArgumentException - when data in address request is not valid.
     */
    public Address saveForUser(AddressRequest addressRequest) {
        try {
            ValidationAddress.allFieldsBlank(addressRequest);
        } catch (IllegalArgumentException e) {
            return null;
        }
        ValidationAddress.validForUser(addressRequest);
        return save(addressRequest);
    }

    /**
     * Validate fields from request and saving new data to old record. When All fields in request are blank,
     * address will be deleted from DB and return null.
     *
     * @param address        which update
     * @param addressRequest new data to save
     * @return {@link Address} which saved in DB with new data or <b>NULL</b> when all fields in addressRequest
     * are blank because this address is deleting from DB.
     * @throws IllegalArgumentException when new data is not valid.
     */
    public Address updateForUser(Address address, AddressRequest addressRequest) {
        try {
            ValidationAddress.allFieldsBlank(addressRequest);
        } catch (IllegalArgumentException e) {
            deleteForUser(address);
            return null;
        }
        ValidationAddress.validForUser(addressRequest);
        prepareAddressRequest(addressRequest);
        State state = getState(addressRequest.getState());
        address.setState(state);
        address.setAddressLine(addressRequest.getAddressLine());
        address.setCity(addressRequest.getCity());
        address.setCode(addressRequest.getCode());
        address.setCountry(addressRequest.getCountry());
        return addressRepository.save(address);
    }

    private void prepareAddressRequest(AddressRequest addressRequest) {
        if (StringUtils.isBlank(addressRequest.getAddressLine())) {
            addressRequest.setAddressLine(null);
        }
        if (StringUtils.isBlank(addressRequest.getCity())) {
            addressRequest.setCity(null);
        }
        if (StringUtils.isBlank(addressRequest.getCode())) {
            addressRequest.setCode(null);
        }
        if (StringUtils.isBlank(addressRequest.getCountry())) {
            addressRequest.setCountry(null);
        }
    }

    public Address saveForBusiness(AddressRequest addressRequest) {
        ValidationAddress.validForBusiness(addressRequest);
        return save(addressRequest);
    }

    public Address updateForBusiness(AddressRequest addressRequest) {
        return null;
    }

    private Address save(AddressRequest addressRequest) {
        prepareAddressRequest(addressRequest);
        State state = getState(addressRequest.getState());
        Address address = Address.builder()
                .addressLine(addressRequest.getAddressLine())
                .city(addressRequest.getCity())
                .code(addressRequest.getCode())
                .country(addressRequest.getCountry())
                .state(state)
                .build();
        return addressRepository.save(address);
    }

    private void deleteForUser(Address address) {
        addressRepository.deleteById(address.getId());
    }

    private State getState(String state) {
        if (StringUtils.isNotBlank(state)) {
            return stateService.findByName(state);
        }
        return null;
    }


    public AddressResponse update(AddressRequest addressRequest) {
        throw new Error("Method Not implement -");
    }

    private void deleteStateFromAddress(Long addressId) {
        addressRepository.deleteStateByAddressId(addressId);
    }

    public List<AddressResponse> findAll() {
        return addressRepository
                .findAll()
                .stream()
                .map(address -> new AddressToAddressResponse().convert(address))
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
