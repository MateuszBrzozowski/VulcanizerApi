package pl.mbrzozowski.vulcanizer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mbrzozowski.vulcanizer.repository.StandRepository;

@RequiredArgsConstructor
@Service
public class StandService {
    private final StandRepository standRepository;


}
