package pl.mbrzozowski.vulcanizer.enums;

public enum Authorities {
    USER("user");

    private final String authority;

    Authorities(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return this.authority;
    }

    @Override
    public String toString() {
        return "Authorities{" +
                "authority='" + authority + '\'' +
                '}';
    }
}
