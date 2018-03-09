package microservice.book.socialmultiplication.service;

import microservice.book.socialmultiplication.domain.Multiplication;
import microservice.book.socialmultiplication.domain.MultiplicationResultAttempt;

import java.util.List;

/**
 * Created by Administrator on 2/14/2018.
 */
public interface MultiplicationService {

    /**
     * Creates a Multiplication object with two randomy-generated factors
     * between 11 and 99
     * @return a Multiplication object with random
     */
    Multiplication createRandomMultiplication();

    /**
     * @return true if the attempt matches the result of the multiplication, false otherwise
     */
    boolean checkAttempt(final MultiplicationResultAttempt resultAttempt);

    List<MultiplicationResultAttempt> getStatsForTheUser(String userAlias);

    MultiplicationResultAttempt getOne(Long id);
}
