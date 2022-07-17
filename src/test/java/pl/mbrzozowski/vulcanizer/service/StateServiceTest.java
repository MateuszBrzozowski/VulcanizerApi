package pl.mbrzozowski.vulcanizer.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.mbrzozowski.vulcanizer.entity.State;
import pl.mbrzozowski.vulcanizer.exceptions.IllegalArgumentException;
import pl.mbrzozowski.vulcanizer.exceptions.NoSuchElementException;
import pl.mbrzozowski.vulcanizer.exceptions.NullPointerException;
import pl.mbrzozowski.vulcanizer.repository.StateRepository;

import java.util.Optional;

import static org.mockito.Mockito.*;

class StateServiceTest {
    //TODO
    //Usuwanie
    //Nie znajduje ID
    //Wyszukiwanie
    //Nie znajduje po id
    //nie znajduje nazwy
    //znajduje nazwe i zwraca
    //znajduje id i zwraca
    //Aktualizowanie
    //Aktualizowanie jednego wojewodzta a istnieje juz taka nazwa
    private StateService stateService;
    private StateRepository stateRepository;
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @BeforeEach
    public void beforeEach() {
        stateRepository = mock(StateRepository.class);
        stateService = new StateService(stateRepository);
    }

    /**
     * Generator of String of specific length
     *
     * @param length MAX_LENGTH = 1000, MIN_LENGTH = 0
     * @return Sequence of chars like string
     */
    private String generateString(int length) {
        StringBuilder result = new StringBuilder();
        final int MAX_LENGTH = 1000;
        if (length <= 0) {
            return result.toString();
        }
        if (length > MAX_LENGTH) {
            length = MAX_LENGTH;
        }
        result.append("a".repeat(length));
        return String.valueOf(result);
    }

    @Test
    void save_addNewState_Success() {
        State state = State.builder()
                .name("Łódzkie")
                .build();
        stateService.save(state);
        verify(stateRepository).save(state);
    }

    @Test
    void save_NamesMustBeUnique_ThrowIllegalArgumentException() {
        State state = State.builder()
                .id(1L)
                .name("Łódzkie")
                .build();
        State stateSecond = State.builder()
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
        State stateSecond = State.builder()
                .id(2L)
                .name("Mazowieckie")
                .build();
        when(stateRepository.findByName("Łódzkie")).thenReturn(Optional.ofNullable(state));
        Assertions.assertDoesNotThrow(() -> stateService.save(stateSecond));
    }

    @Test
    void save_EmptyStateNameCanNotAdd_ThrowIllegalArgumentException() {
        State state = State.builder()
                .id(1L)
                .name("")
                .build();
        Assertions.assertThrows(IllegalArgumentException.class, () -> stateService.save(state));
    }

    @Test
    void save_NullStateNameCanNotAdd_ThrowIllegalArgumentException() {
        State state = State.builder()
                .name(null)
                .build();
        Assertions.assertThrows(NullPointerException.class, () -> stateService.save(state));
    }

    @Test
    void save_StateNameToLong_ThrowIllegalArgumentException() {
        State state = State.builder()
                .name(generateString(51))
                .build();
        Assertions.assertThrows(IllegalArgumentException.class, () -> stateService.save(state));
    }

    @Test
    void save_StateName50Chars_ThrowIllegalArgumentException() {
        State state = State.builder()
                .name(generateString(50))
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
        Assertions.assertThrows(NoSuchElementException.class, () -> stateService.findById(id));
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
        Assertions.assertThrows(NoSuchElementException.class, () -> stateService.findByName(name));
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
        Assertions.assertThrows(NoSuchElementException.class, () -> stateService.findByName(name));
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
        when(stateRepository.findById(id)).thenReturn(Optional.ofNullable(state));
        stateService.update(state);
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
        State stateSecond = State.builder()
                .id(2L)
                .name(name)
                .build();
        when(stateRepository.findById(2L)).thenReturn(Optional.ofNullable(state));
        when(stateRepository.findByName(name)).thenReturn(Optional.ofNullable(state));
        Assertions.assertThrows(IllegalArgumentException.class, () -> stateService.update(stateSecond));
    }

}