package pl.mbrzozowski.vulcanizer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressRequest {
    private Long id;
    private String addressLine;
    private String city;
    private String code;
    private String state;
    private String country;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressRequest that = (AddressRequest) o;
        return Objects.equals(addressLine, that.addressLine) && Objects.equals(city, that.city) && Objects.equals(code, that.code) && Objects.equals(state, that.state) && Objects.equals(country, that.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(addressLine, city, code, state, country);
    }
}
