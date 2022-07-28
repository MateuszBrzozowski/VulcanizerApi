package pl.mbrzozowski.vulcanizer.constant;

public class SecurityConstant {
    private static final long EXPIRATION_TIME = 432_000_000; // miliseconds - now for tests 5 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String JWT_TOKEN_HEADER = "Jwt-Token";
    public static final String TOKEN_CANNOT_BE_VERIFIED = "Token cannot be verified";
    public static final String GET_COMPANY_LLC = "Company, LLC";
    public static final String GET_COMPANY_ADMINISTRATION = "Vulcanizer App";
    public static final String AUTHORITIES = "Authorities";
    public static final String FORBIDDEN_MESSAGE = "You need to log in to access this page";
    public static final String ACCESS_DENIED_MESSAGE = "You do not have permission to access this page";
    public static final String OPTIONS_HTTP_METHOD = "Options";
    public static final String[] PUBLIC_URLS = {"/user/login", "user/register", "user/resetpassword/**", "/user/image/**", "/"};
}
