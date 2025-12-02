package co.istad.itpmongdb.repository;

import co.istad.itpmongdb.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    @Query("{name: {$regex: ?0, $options: 'i'} }")
    List<User> filterByName(String name);

    @Query("{ $or:  [{city : ?0}, {age: {$eq: ?1}}]}")
    List<User> filter(String city, Integer age);
}
