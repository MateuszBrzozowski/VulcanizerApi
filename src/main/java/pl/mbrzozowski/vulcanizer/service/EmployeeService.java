package pl.mbrzozowski.vulcanizer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mbrzozowski.vulcanizer.entity.Employee;
import pl.mbrzozowski.vulcanizer.repository.EmployeeRepository;

@RequiredArgsConstructor
@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;


    public Employee findById(Long id) {
        return employeeRepository
                .findById(id)
                .orElseThrow(() -> {
                    throw new IllegalArgumentException(String.format("Not found employee by id [%s]", id));
                });
    }

    public void deleteById(Long id) {
        findById(id);
        employeeRepository.deleteById(id);
    }
}
