package microservice.book.socialmultiplication.repository;

import microservice.book.socialmultiplication.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Created by Administrator on 2/23/2018.
 */
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByAlias(final String alias);
}
