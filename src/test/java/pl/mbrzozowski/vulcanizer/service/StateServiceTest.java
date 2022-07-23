package pl.mbrzozowski.vulcanizer.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.mbrzozowski.vulcanizer.dto.StateRequest;
import pl.mbrzozowski.vulcanizer.entity.State;
import pl.mbrzozowski.vulcanizer.exceptions.IllegalArgumentException;
import pl.mbrzozowski.vulcanizer.repository.StateRepository;
import pl.mbrzozowski.vulcanizer.util.StringGenerator;

import java.util.Optional;

import static org.mockito.Mockito.*;

class StateServiceTest {
    private StateService stateService;
    private StateRepository stateRepository;
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @BeforeEach
    public void beforeEach() {
        stateRepository = mock(StateRepository.class);
        stateService = new StateService(stateRepository);
    }


    @Test
    void save_addNewState_Success() {
        StateRequest stateRequest = StateRequest.builder()
                .name("Łódzkie")
                .build();
        State state = State.builder()
                .name("Łódzkie")
                .build();
        stateService.save(stateRequest);
        verify(stateRepository).save(state);
    }

    @Test
    void save_NamesMustBeUnique_ThrowIllegalArgumentException() {
        State state = State.builder()
                .id(1L)
                .name("Łódzkie")
                .build();
        StateRequest stateSecond = StateRequest.builder()
                .id(2L)
                .name("Łódzkie")
                .build();
        when(stateRepository.findByName("Łódzkie")).thenReturn(Optional.ofNullable(state));
        Assertions.assertThrows(IllegalArgumentException.class, () -> stateService.save(stateSecond));
    }

    @Test
    void save_TwoDifferentNames_DoesNotThrow() {
        State state = State.builder()
                .id(1L)
                .name("Łódzkie")
                .build();
        StateRequest stateSecond = StateRequest.builder()
                .id(2L)
                .name("Mazowieckie")
                .build();
        when(stateRepository.findByName("Łódzkie")).thenReturn(Optional.ofNullable(state));
        Assertions.assertDoesNotThrow(() -> stateService.save(stateSecond));
    }

    @Test
    void save_EmptyStateNameCanNotAdd_ThrowIllegalArgumentException() {
        StateRequest state = StateRequest.builder()
                .id(1L)
                .name("")
                .build();
        Assertions.assertThrows(IllegalArgumentException.class, () -> stateService.save(state));
    }

    @Test
    void save_NullStateNameCanNotAdd_ThrowIllegalArgumentException() {
        StateRequest state = StateRequest.builder()
                .name(null)
                .build();
        Assertions.assertThrows(IllegalArgumentException.class, () -> stateService.save(state));
    }

    @Test
    void save_StateNameToLong_ThrowIllegalArgumentException() {
        StateRequest state = StateRequest.builder()
                .name(new StringGenerator().apply(51))
                .build();
        Assertions.assertThrows(IllegalArgumentException.class, () -> stateService.save(state));
    }

    @Test
    void save_StateName50Chars_ThrowIllegalArgumentException() {
        StateRequest state = StateRequest.builder()
                .name(new StringGenerator().apply(50))
                .build();
        Assertions.assertDoesNotThrow(() -> stateService.save(state));
    }


    @Test
    void findById_Found_DoesNotThrow() {
        String name = "Łódzkie";
        long id = 1L;
        State state = State.builder()
                .id(id)
                .name(name)
                .build();
        when(stateRepository.findById(id)).thenReturn(Optional.of(state));
        Assertions.assertDoesNotThrow(() -> stateService.findById(id));
    }

    @Test
    void findById_DoesNotFound_Throw() {
        long id = 1L;
        when(stateRepository.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(IllegalArgumentException.class, () -> stateService.findById(id));
    }

    @Test
    void findByName_Found_DoesNotThrow() {
        String name = "Łódzkie";
        long id = 1L;
        State state = State.builder()
                .id(id)
                .name(name)
                .build();
        when(stateRepository.findByName(name)).thenReturn(Optional.ofNullable(state));
        Assertions.assertDoesNotThrow(() -> stateService.findByName(name));
    }

    @Test
    void findByName_DoesNotFound_ThrowNoSuchElementException() {
        String name = "Łódzkie";
        when(stateRepository.findByName(name)).thenReturn(Optional.empty());
        Assertions.assertThrows(IllegalArgumentException.class, () -> stateService.findByName(name));
    }

    @Test
    void findByName_FindingByExpresion_DoesNotThrow() {
        String name = "Maz";
        String serachExpresion = "az";
        long id = 1L;
        State state = State.builder()
                .id(id)
                .name(name)
                .build();
        when(stateRepository.findByName(serachExpresion)).thenReturn(Optional.ofNullable(state));
        Assertions.assertDoesNotThrow(() -> stateService.findByName(serachExpresion));
    }

    @Test
    void findByName_NoFound_ThrowNoSuchElementException() {
        String name = "za";
        when(stateRepository.findByName(name)).thenReturn(Optional.empty());
        Assertions.assertThrows(IllegalArgumentException.class, () -> stateService.findByName(name));
    }

    @Test
    void findAll_VerfifyExecuteRepository() {
        State state = State.builder()
                .id(1L)
                .name("Łódzkie")
                .build();
        stateService.findAll();
        verify(stateRepository).findAll();
    }

    @Test
    void update_UpdateNameOfState_OK() {
        String name = "Łódzkie";
        long id = 1L;
        State state = State.builder()
                .id(id)
                .name(name)
                .build();
        StateRequest stateRequest = StateRequest.builder()
                .id(id)
                .name(name)
                .build();
        when(stateRepository.findById(id)).thenReturn(Optional.ofNullable(state));
        stateService.update(stateRequest);
        verify(stateRepository).save(state);
    }

    @Test
    void update_NameIsExist_ThrowIllegalArgumentException() {
        String name = "Łódzkie";
        long id = 1L;
        State state = State.builder()
                .id(id)
                .name(name)
                .build();
        StateRequest stateRequest = StateRequest.builder()
                .id(2L)
                .name(name)
                .build();
        when(stateRepository.findById(2L)).thenReturn(Optional.ofNullable(state));
        when(stateRepository.findByName(name)).thenReturn(Optional.ofNullable(state));
        Assertions.assertThrows(IllegalArgumentException.class, () -> stateService.update(stateRequest));
    }

}