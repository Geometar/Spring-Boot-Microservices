package microservices.book.gamification.client.dto;

/**
 * Created by Administrator on 3/1/2018.
 */
public interface MultiplicationResultAttemptClient {

    MultiplicationResultAttempt retrieveMultiplicationResultAttemptById(final Long multiplicationId);
}
