package pl.mbrzozowski.vulcanizer.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.mbrzozowski.vulcanizer.dto.EmployeeRequest;
import pl.mbrzozowski.vulcanizer.entity.Business;
import pl.mbrzozowski.vulcanizer.entity.Employee;
import pl.mbrzozowski.vulcanizer.entity.EmployeeRole;
import pl.mbrzozowski.vulcanizer.entity.User;
import pl.mbrzozowski.vulcanizer.exceptions.IllegalArgumentException;
import pl.mbrzozowski.vulcanizer.exceptions.NoSuchElementException;
import pl.mbrzozowski.vulcanizer.exceptions.UserWasNotFoundException;
import pl.mbrzozowski.vulcanizer.repository.BusinessRepository;
import pl.mbrzozowski.vulcanizer.repository.EmployeeRepository;
import pl.mbrzozowski.vulcanizer.repository.EmployeeRoleRepository;
import pl.mbrzozowski.vulcanizer.repository.UserRepository;

import static org.mockito.Mockito.*;

class EmployeeServiceTest {
    private EmployeeService employeeService;
    private EmployeeRepository employeeRepository;
    private BusinessService businessService;
    private BusinessRepository businessRepository;
    private UserService userService;
    private UserRepository userRepository;
    private EmployeeRoleService employeeRoleService;
    private EmployeeRoleRepository employeeRoleRepository;

    @BeforeEach
    public void beforeEach() {
        employeeRepository = mock(EmployeeRepository.class);
        businessService = mock(BusinessService.class);
        businessRepository = mock(BusinessRepository.class);
        userService = mock(UserService.class);
        userRepository = mock(UserRepository.class);
        employeeRoleService = mock(EmployeeRoleService.class);
        employeeRoleRepository = mock(EmployeeRoleRepository.class);
        employeeService = new EmployeeService(employeeRepository, businessService, userService, employeeRoleService);
    }

    //
    // SAVE
    //

    @Test
    void save_Success() {
        EmployeeRequest employeeRequest = EmployeeRequest.builder()
                .id(1L)
                .userId(1L)
                .businessId(1L)
                .roleId(1L)
                .build();

        User user = new User();
        Business business = new Business();
        EmployeeRole role = new EmployeeRole();
        Employee employee = Employee.builder()
                .userId(user)
                .businessId(business)
                .roleId(role)
                .build();
        when(employeeRoleService.findById(1L)).thenReturn(role);
        when(businessService.findById(1L)).thenReturn(business);
        when(userService.findById(1L)).thenReturn(user);
        Assertions.assertDoesNotThrow(() -> employeeService.save(employeeRequest));
        verify(employeeRepository).save(employee);
    }

    @Test
    void save_NoUserId_ThrowIllegalArgumentException() {
        EmployeeRequest employeeRequest = EmployeeRequest.builder()
                .id(1L)
//                .userId(1L)
                .businessId(1L)
                .roleId(1L)
                .build();
        Assertions.assertThrows(IllegalArgumentException.class, () -> employeeService.save(employeeRequest));
    }

    @Test
    void save_NoBusinessId_ThrowIllegalArgumentException() {
        EmployeeRequest employeeRequest = EmployeeRequest.builder()
                .id(1L)
                .userId(1L)
//                .businessId(1L)
                .roleId(1L)
                .build();
        Assertions.assertThrows(IllegalArgumentException.class, () -> employeeService.save(employeeRequest));
    }

    @Test
    void save_NoRoleId_ThrowIllegalArgumentException() {
        EmployeeRequest employeeRequest = EmployeeRequest.builder()
                .id(1L)
                .userId(1L)
                .businessId(1L)
//                .roleId(1L)
                .build();
        Assertions.assertThrows(IllegalArgumentException.class, () -> employeeService.save(employeeRequest));
    }

    @Test
    void save_NoEmployeeRole_DoesNotThrow() {
        EmployeeRequest employeeRequest = EmployeeRequest.builder()
//                .id(1L)
                .userId(1L)
                .businessId(1L)
                .roleId(1L)
                .build();
        Assertions.assertDoesNotThrow(() -> employeeService.save(employeeRequest));
    }

    @Test
    void save_UserIsNotExist_ThrowIllegalArgumentException() {
        EmployeeRequest employeeRequest = EmployeeRequest.builder()
                .id(1L)
                .userId(1L)
                .businessId(1L)
                .roleId(1L)
                .build();

        User user = new User();
        Business business = new Business();
        EmployeeRole role = new EmployeeRole();
        Employee employee = Employee.builder()
                .userId(user)
                .businessId(business)
                .roleId(role)
                .build();
        when(employeeRoleService.findById(1L)).thenReturn(role);
        when(businessService.findById(1L)).thenReturn(business);
        when(userService.findById(1L)).thenThrow(UserWasNotFoundException.class);
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> employeeService.save(employeeRequest));
    }

    @Test
    void save_BusinessIsNotExist_ThrowIllegalArgumentException() {
        EmployeeRequest employeeRequest = EmployeeRequest.builder()
                .id(1L)
                .userId(1L)
                .businessId(1L)
                .roleId(1L)
                .build();

        User user = new User();
        Business business = new Business();
        EmployeeRole role = new EmployeeRole();
        Employee employee = Employee.builder()
                .userId(user)
                .businessId(business)
                .roleId(role)
                .build();
        when(employeeRoleService.findById(1L)).thenReturn(role);
        when(businessService.findById(1L)).thenThrow(NoSuchElementException.class);
        when(userService.findById(1L)).thenReturn(user);
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> employeeService.save(employeeRequest));
    }

    @Test
    void save_RoleIsNotExist_ThrowIllegalArgumentException() {
        EmployeeRequest employeeRequest = EmployeeRequest.builder()
                .id(1L)
                .userId(1L)
                .businessId(1L)
                .roleId(1L)
                .build();

        User user = new User();
        Business business = new Business();
        EmployeeRole role = new EmployeeRole();
        when(employeeRoleService.findById(1L)).thenThrow(NoSuchElementException.class);
        when(businessService.findById(1L)).thenReturn(business);
        when(userService.findById(1L)).thenReturn(user);
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> employeeService.save(employeeRequest));
    }


}