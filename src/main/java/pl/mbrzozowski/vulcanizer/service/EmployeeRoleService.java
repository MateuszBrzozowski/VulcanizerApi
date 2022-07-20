package pl.mbrzozowski.vulcanizer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mbrzozowski.vulcanizer.dto.EmployeeRoleRequest;
import pl.mbrzozowski.vulcanizer.dto.EmployeeRoleResponse;
import pl.mbrzozowski.vulcanizer.entity.EmployeeRole;
import pl.mbrzozowski.vulcanizer.exceptions.NoSuchElementException;
import pl.mbrzozowski.vulcanizer.repository.EmployeeRoleRepository;
import pl.mbrzozowski.vulcanizer.validation.ValidationEmployeeRole;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeRoleService implements ServiceLayer<EmployeeRoleRequest, EmployeeRoleResponse, EmployeeRole> {
    private final EmployeeRoleRepository employeeRoleRepository;

    @Override
    public EmployeeRole save(EmployeeRoleRequest employeeRoleRequest) {
        ValidationEmployeeRole.valid(employeeRoleRequest);
        return employeeRoleRepository.save(new EmployeeRole(employeeRoleRequest.getName()));
    }

    @Override
    public EmployeeRoleResponse update(EmployeeRoleRequest employeeRoleRequest) {
        ValidationEmployeeRole.validBeforeUpdate(employeeRoleRequest);
        EmployeeRole role = new EmployeeRole(employeeRoleRequest.getId(), employeeRoleRequest.getName());
        findById(employeeRoleRequest.getId());
        employeeRoleRepository.save(role);
        return null;
    }

    @Override
    public List<EmployeeRoleResponse> findAll() {
        return employeeRoleRepository
                .findAll()
                .stream()
                .map(employeeRole -> new EmployeeRoleResponse(employeeRole.getName()))
                .collect(Collectors.toList());
    }

    public List<EmployeeRoleResponse> findAllForBusiness(Long businessId) {
        return null;
    }

    @Override
    public EmployeeRole findById(Long id) {
        return employeeRoleRepository
                .findById(id)
                .orElseThrow(() -> {
                    throw new NoSuchElementException(String.format("Not found role by id [%s]", id));
                });
    }


    @Override
    public void deleteById(Long id) {
        findById(id);
        employeeRoleRepository.deleteById(id);
    }
}
