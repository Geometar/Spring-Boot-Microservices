package microservice.book.socialmultiplication.service.impl;

import microservice.book.socialmultiplication.service.RandomGeneratorService;
import org.springframework.stereotype.Service;
import java.util.Random;

/**
 * Created by Administrator on 2/20/2018.
 */

@Service
public class RandomGeneratorServiceImpl implements RandomGeneratorService {

    final static int MINIMUM_FACTOR = 11;
    final static int MAXIMUM_FACTOR = 99;

    @Override
    public int generateRandomFactor() {
        return new Random().nextInt((MAXIMUM_FACTOR - MINIMUM_FACTOR) +1) + MINIMUM_FACTOR;
    }
}
