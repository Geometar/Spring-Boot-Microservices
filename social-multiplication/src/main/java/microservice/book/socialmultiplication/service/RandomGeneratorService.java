package microservice.book.socialmultiplication.service;

/**
 * Created by Administrator on 2/14/2018.
 */
public interface RandomGeneratorService {
    /**
     *
     * @return a randomly-generated factor. It's always a number between 11 and 99
     */
    int generateRandomFactor();
}
