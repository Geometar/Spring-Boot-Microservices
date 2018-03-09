package microservice.book.socialmultiplication.repository;

import microservice.book.socialmultiplication.domain.Multiplication;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Administrator on 2/23/2018.
 */
public interface MultiplicationRepository extends CrudRepository<Multiplication, Long> {
}
