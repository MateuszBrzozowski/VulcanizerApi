package pl.mbrzozowski.vulcanizer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mbrzozowski.vulcanizer.entity.Business;
import pl.mbrzozowski.vulcanizer.entity.Employee;
import pl.mbrzozowski.vulcanizer.entity.User;
import pl.mbrzozowski.vulcanizer.repository.EmployeeRepository;

import java.util.List;

import static pl.mbrzozowski.vulcanizer.enums.BusinessRole.OWNER;

@RequiredArgsConstructor
@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public Employee createBusiness(User user, Business business) {
        Employee employee = new Employee(null, user, business, OWNER);
        return employeeRepository.save(employee);
    }


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

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }
}
