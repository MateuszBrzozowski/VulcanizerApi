package pl.mbrzozowski.vulcanizer.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.mbrzozowski.vulcanizer.dto.AddressRequest;
import pl.mbrzozowski.vulcanizer.entity.Address;
import pl.mbrzozowski.vulcanizer.entity.State;

class ComparatorTest {

    @Test
    void compareAddressAndAddressRequest_Same() {
        Address address = Address.builder()
                .build();
        AddressRequest addressRequest = AddressRequest.builder()
                .build();
        boolean compare = Comparator.compare(address, addressRequest);
        Assertions.assertTrue(compare);
    }

    @Test
    void compareAddressAndAddressRequest_SameAddressLine() {
        Address address = Address.builder()
                .addressLine("Line")
                .build();
        AddressRequest addressRequest = AddressRequest.builder()
                .addressLine("line")
                .build();
        boolean compare = Comparator.compare(address, addressRequest);
        Assertions.assertTrue(compare);
    }

    @Test
    void compareAddressAndAddressRequest_NotSameAddressLine() {
        Address address = Address.builder()
                .addressLine("Line")
                .build();
        AddressRequest addressRequest = AddressRequest.builder()
                .addressLine("test")
                .build();
        boolean compare = Comparator.compare(address, addressRequest);
        Assertions.assertFalse(compare);
    }

    @Test
    void compareAddressAndAddressRequest_SameAddressLineWithBlank() {
        Address address = Address.builder()
                .addressLine("Line")
                .build();
        AddressRequest addressRequest = AddressRequest.builder()
                .addressLine("line ")
                .build();
        boolean compare = Comparator.compare(address, addressRequest);
        Assertions.assertTrue(compare);
    }

    @Test
    void compareAddressAndAddressRequest_SameCity() {
        Address address = Address.builder()
                .city("City")
                .build();
        AddressRequest addressRequest = AddressRequest.builder()
                .city("City")
                .build();
        boolean compare = Comparator.compare(address, addressRequest);
        Assertions.assertTrue(compare);
    }

    @Test
    void compareAddressAndAddressRequest_NotSameCity() {
        Address address = Address.builder()
                .city("City")
                .build();
        AddressRequest addressRequest = AddressRequest.builder()
                .city("BigCity")
                .build();
        boolean compare = Comparator.compare(address, addressRequest);
        Assertions.assertFalse(compare);
    }

    @Test
    void compareAddressAndAddressRequest_SameCityWithBlank() {
        Address address = Address.builder()
                .city("City")
                .build();
        AddressRequest addressRequest = AddressRequest.builder()
                .city("City ")
                .build();
        boolean compare = Comparator.compare(address, addressRequest);
        Assertions.assertTrue(compare);
    }

    @Test
    void compareAddressAndAddressRequest_SameCode() {
        Address address = Address.builder()
                .code("99-999")
                .build();
        AddressRequest addressRequest = AddressRequest.builder()
                .code("99-999")
                .build();
        boolean compare = Comparator.compare(address, addressRequest);
        Assertions.assertTrue(compare);
    }

    @Test
    void compareAddressAndAddressRequest_NotSameCode() {
        Address address = Address.builder()
                .code("99-999")
                .build();
        AddressRequest addressRequest = AddressRequest.builder()
                .code("88-889")
                .build();
        boolean compare = Comparator.compare(address, addressRequest);
        Assertions.assertFalse(compare);
    }

    @Test
    void compareAddressAndAddressRequest_SameCodeWithBlank() {
        Address address = Address.builder()
                .code("99-999")
                .build();
        AddressRequest addressRequest = AddressRequest.builder()
                .code("99-999 ")
                .build();
        boolean compare = Comparator.compare(address, addressRequest);
        Assertions.assertTrue(compare);
    }

    @Test
    void compareAddressAndAddressRequest_SameState() {
        State state = new State("State");
        Address address = Address.builder()
                .state(state)
                .build();
        AddressRequest addressRequest = AddressRequest.builder()
                .state("State")
                .build();
        boolean compare = Comparator.compare(address, addressRequest);
        Assertions.assertTrue(compare);
    }

    @Test
    void compareAddressAndAddressRequest_NotSameState() {
        State state = new State("State");
        Address address = Address.builder()
                .state(state)
                .build();
        AddressRequest addressRequest = AddressRequest.builder()
                .state("States")
                .build();
        boolean compare = Comparator.compare(address, addressRequest);
        Assertions.assertFalse(compare);
    }

    @Test
    void compareAddressAndAddressRequest_SameStateWithBlank() {
        State state = new State("State");
        Address address = Address.builder()
                .state(state)
                .build();
        AddressRequest addressRequest = AddressRequest.builder()
                .state("State ")
                .build();
        boolean compare = Comparator.compare(address, addressRequest);
        Assertions.assertTrue(compare);
    }

    @Test
    void compareAddressAndAddressRequest_SameCountry() {
        Address address = Address.builder()
                .country("Country")
                .build();
        AddressRequest addressRequest = AddressRequest.builder()
                .country("Country")
                .build();
        boolean compare = Comparator.compare(address, addressRequest);
        Assertions.assertTrue(compare);
    }

    @Test
    void compareAddressAndAddressRequest_NotSameCountry() {
        Address address = Address.builder()
                .country("Country")
                .build();
        AddressRequest addressRequest = AddressRequest.builder()
                .country("SmallCountry")
                .build();
        boolean compare = Comparator.compare(address, addressRequest);
        Assertions.assertFalse(compare);
    }

    @Test
    void compareAddressAndAddressRequest_SameCountryWithBlank() {
        Address address = Address.builder()
                .country("Country")
                .build();
        AddressRequest addressRequest = AddressRequest.builder()
                .country("Country ")
                .build();
        boolean compare = Comparator.compare(address, addressRequest);
        Assertions.assertTrue(compare);
    }

    @Test
    void compareAddressAndAddressRequest_SameAllAddress() {
        State state = new State("State");
        Address address = Address.builder()
                .addressLine("Line")
                .city("city")
                .code("99-999")
                .state(state)
                .country("Country")
                .build();
        AddressRequest addressRequest = AddressRequest.builder()
                .addressLine("line ")
                .city("city")
                .code("99-999")
                .state("state")
                .country("Country")
                .build();
        boolean compare = Comparator.compare(address, addressRequest);
        Assertions.assertTrue(compare);
    }
}