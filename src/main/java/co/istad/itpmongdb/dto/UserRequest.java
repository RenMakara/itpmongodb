package co.istad.itpmongdb.dto;

public record UserRequest(
        String name,
        String username,
        String email
) {
}
