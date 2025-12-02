package co.istad.itpmongdb.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document( collection = "users")
public class User {
    @Id
    private String id;
    private String name;
    private String username;
    private String email;
    private Integer age;
    private String city;
}


