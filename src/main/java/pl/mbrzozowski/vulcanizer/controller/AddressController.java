package pl.mbrzozowski.vulcanizer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mbrzozowski.vulcanizer.dto.AddressRequest;
import pl.mbrzozowski.vulcanizer.dto.AddressResponse;
import pl.mbrzozowski.vulcanizer.service.AddressService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/address")
public class AddressController {
    private final AddressService addressService;

    @GetMapping
    public ResponseEntity<List<AddressResponse>> findAll() {
        List<AddressResponse> responseList = addressService.findAll();
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressResponse> findById(@PathVariable("id") Long id) {
        AddressResponse addressResponse = addressService.findById(id);
        return new ResponseEntity<>(addressResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody AddressRequest addressRequest) {
        addressService.save(addressRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<AddressResponse> update(@RequestBody AddressRequest addressRequest) {
        AddressResponse addressResponse = addressService.update(addressRequest);
        return new ResponseEntity<>(addressResponse, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        addressService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
