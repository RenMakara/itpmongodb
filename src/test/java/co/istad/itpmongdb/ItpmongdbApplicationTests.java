package co.istad.itpmongdb;

import co.istad.itpmongdb.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ItpmongdbApplicationTests {

    @Autowired
    private UserRepository userRepository;

	@Test
    void testFindUserByName_Query(){
        System.out.println(userRepository.filterByName("clementina duBuque"));
    }

    @Test
    void testFindUserByAge_Query(){
        System.out.println(userRepository.filter("PP", 29));
    }

}
