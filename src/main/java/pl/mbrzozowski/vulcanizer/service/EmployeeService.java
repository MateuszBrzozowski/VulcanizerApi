package pl.mbrzozowski.vulcanizer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mbrzozowski.vulcanizer.dto.EmployeeRequest;
import pl.mbrzozowski.vulcanizer.dto.EmployeeResponse;
import pl.mbrzozowski.vulcanizer.dto.mapper.EmployeeRequestToEmployee;
import pl.mbrzozowski.vulcanizer.dto.mapper.EmployeeToEmployeeResponse;
import pl.mbrzozowski.vulcanizer.entity.Employee;
import pl.mbrzozowski.vulcanizer.exceptions.NoSuchElementException;
import pl.mbrzozowski.vulcanizer.repository.EmployeeRepository;
import pl.mbrzozowski.vulcanizer.validation.ValidationEmployee;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EmployeeService implements ServiceLayer<EmployeeRequest, EmployeeResponse, Employee> {
    private final EmployeeRepository employeeRepository;
    private final UserService userService;

    @Override
    public Employee save(EmployeeRequest employeeRequest) {
        employeeRequest.setId(null);
        ValidationEmployee.valid(employeeRequest);
        Employee employee =
                new EmployeeRequestToEmployee(userService)
                        .convert(employeeRequest);
        return employeeRepository.save(employee);
    }

    @Override
    public EmployeeResponse update(EmployeeRequest employeeRequest) {
        return null;
    }

    @Override
    public List<EmployeeResponse> findAll() {
        return employeeRepository
                .findAll()
                .stream()
                .map(EmployeeToEmployeeResponse::convert)
                .collect(Collectors.toList());
    }

    @Override
    public Employee findById(Long id) {
        return employeeRepository
                .findById(id)
                .orElseThrow(() -> {
                    throw new NoSuchElementException(String.format("Not found employee by id [%s]", id));
                });
    }

    @Override
    public void deleteById(Long id) {
        findById(id);
        employeeRepository.deleteById(id);
    }
}
