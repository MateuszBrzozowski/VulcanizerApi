package pl.mbrzozowski.vulcanizer.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.mbrzozowski.vulcanizer.dto.EmployeeRoleRequest;
import pl.mbrzozowski.vulcanizer.entity.EmployeeRole;
import pl.mbrzozowski.vulcanizer.exceptions.IllegalArgumentException;
import pl.mbrzozowski.vulcanizer.exceptions.NoSuchElementException;
import pl.mbrzozowski.vulcanizer.repository.EmployeeRoleRepository;
import pl.mbrzozowski.vulcanizer.util.StringGenerator;

import java.util.Optional;

import static org.mockito.Mockito.*;

class EmployeeRoleServiceTest {

    private EmployeeRoleService employeeRoleService;
    private EmployeeRoleRepository employeeRoleRepository;

    @BeforeEach
    public void beforeEach() {
        employeeRoleRepository = mock(EmployeeRoleRepository.class);
        employeeRoleService = new EmployeeRoleService(employeeRoleRepository);
    }

    //
    // SAVE
    //

    @Test
    void save_Success() {
        EmployeeRoleRequest employeeRoleRequest = new EmployeeRoleRequest("Name");
        EmployeeRole employeeRole = new EmployeeRole("Name");
        employeeRoleService.save(employeeRoleRequest);
        verify(employeeRoleRepository).save(employeeRole);
    }

    @Test
    void save_NullName_ThrowIllegalArgumentException() {
        EmployeeRoleRequest employeeRoleRequest = new EmployeeRoleRequest(null);
        Assertions.assertThrows(IllegalArgumentException.class, () -> employeeRoleService.save(employeeRoleRequest));
    }

    @Test
    void save_NulRequest_ThrowIllegalArgumentException() {
        EmployeeRoleRequest employeeRoleRequest = null;
        Assertions.assertThrows(IllegalArgumentException.class, () -> employeeRoleService.save(employeeRoleRequest));
    }

    @Test
    void save_EmptyName_ThrowIllegalArgumentException() {
        EmployeeRoleRequest employeeRoleRequest = new EmployeeRoleRequest("");
        Assertions.assertThrows(IllegalArgumentException.class, () -> employeeRoleService.save(employeeRoleRequest));
    }

    @Test
    void save_EmptyToLong_ThrowIllegalArgumentException() {
        EmployeeRoleRequest employeeRoleRequest = new EmployeeRoleRequest(new StringGenerator().apply(101));
        Assertions.assertThrows(IllegalArgumentException.class, () -> employeeRoleService.save(employeeRoleRequest));
    }

    @Test
    void save_EmptyMaxLength_DoesNotThrow() {
        EmployeeRoleRequest employeeRoleRequest = new EmployeeRoleRequest(new StringGenerator().apply(100));
        Assertions.assertDoesNotThrow(() -> employeeRoleService.save(employeeRoleRequest));
    }

    //
    // UPDATE
    //

    @Test
    void update_Success() {
        EmployeeRoleRequest employeeRoleRequest = new EmployeeRoleRequest(1L, "Name");
        EmployeeRole employeeRole = new EmployeeRole(1L, "Name");
        when(employeeRoleRepository.findById(1L)).thenReturn(Optional.of(employeeRole));
        employeeRoleService.update(employeeRoleRequest);
        verify(employeeRoleRepository).save(employeeRole);
    }

    @Test
    void update_NullName_ThrowIllegalArgumentException() {
        EmployeeRoleRequest employeeRoleRequest = new EmployeeRoleRequest(null);
        Assertions.assertThrows(IllegalArgumentException.class, () -> employeeRoleService.update(employeeRoleRequest));
    }

    @Test
    void update_NulRequest_ThrowIllegalArgumentException() {
        EmployeeRoleRequest employeeRoleRequest = null;
        Assertions.assertThrows(IllegalArgumentException.class, () -> employeeRoleService.update(employeeRoleRequest));
    }

    @Test
    void update_EmptyName_ThrowIllegalArgumentException() {
        EmployeeRoleRequest employeeRoleRequest = new EmployeeRoleRequest("");
        Assertions.assertThrows(IllegalArgumentException.class, () -> employeeRoleService.update(employeeRoleRequest));
    }

    @Test
    void update_NameToLong_ThrowIllegalArgumentException() {
        EmployeeRoleRequest employeeRoleRequest = new EmployeeRoleRequest(new StringGenerator().apply(101));
        Assertions.assertThrows(IllegalArgumentException.class, () -> employeeRoleService.update(employeeRoleRequest));
    }

    @Test
    void update_NameMaxLength_DoesNotThrow() {
        EmployeeRoleRequest employeeRoleRequest = new EmployeeRoleRequest(1L, new StringGenerator().apply(100));
        EmployeeRole employeeRole = new EmployeeRole(1L, "asdasdasd");
        when(employeeRoleRepository.findById(1L)).thenReturn(Optional.of(employeeRole));
        Assertions.assertDoesNotThrow(() -> employeeRoleService.update(employeeRoleRequest));
    }

    @Test
    void update_NoSuchRole_ThrowNoSuchElementException() {
        EmployeeRoleRequest employeeRoleRequest = new EmployeeRoleRequest(1L, "new name");
        EmployeeRole employeeRole = new EmployeeRole(1L, "asdasdasd");
        when(employeeRoleRepository.findById(1L)).thenThrow(NoSuchElementException.class);
        Assertions.assertThrows(NoSuchElementException.class, () -> employeeRoleService.update(employeeRoleRequest));
    }

    //
    // FIND BY ID
    //

    @Test
    void findById_Success() {
        EmployeeRole employeeRole = new EmployeeRole(1L, "Name");
        when(employeeRoleRepository.findById(1L)).thenReturn(Optional.of(employeeRole));
        Assertions.assertDoesNotThrow(() -> employeeRoleService.findById(1L));
    }

    @Test
    void findByID_NoSuchRole_ThrowNoSuchElementException() {
        when(employeeRoleRepository.findById(1L)).thenThrow(NoSuchElementException.class);
        Assertions.assertThrows(NoSuchElementException.class, () -> employeeRoleService.findById(1L));
    }

}