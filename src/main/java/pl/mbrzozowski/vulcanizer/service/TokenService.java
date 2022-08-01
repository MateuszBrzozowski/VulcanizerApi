package pl.mbrzozowski.vulcanizer.service;

import pl.mbrzozowski.vulcanizer.entity.User;

public interface TokenService {

    User confirmToken(String token);

    String createNewToken(User user);
}
