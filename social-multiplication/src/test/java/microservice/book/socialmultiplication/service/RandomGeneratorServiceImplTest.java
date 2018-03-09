package microservice.book.socialmultiplication.service;

import microservice.book.socialmultiplication.service.impl.RandomGeneratorServiceImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Created by Administrator on 2/20/2018.
 */
public class RandomGeneratorServiceImplTest {

    private RandomGeneratorServiceImpl service;

    @Before
    public void setUp(){
        service = new RandomGeneratorServiceImpl();
    }

    @Test
    public void generateRandomFactorIsBetweenExpectedLimits() throws Exception{

        List<Integer> randomFactors = IntStream.range(0, 1000)
                                                .map(i -> service.generateRandomFactor())
                                                .boxed().collect(Collectors.toList());

        assertThat(randomFactors).containsOnlyElementsOf(IntStream.range(11, 100).boxed().collect(Collectors.toList()));
    }
}
