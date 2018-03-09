package microservice.book.socialmultiplication.service;

import microservice.book.socialmultiplication.domain.Multiplication;
import microservice.book.socialmultiplication.domain.MultiplicationResultAttempt;
import microservice.book.socialmultiplication.domain.User;
import microservice.book.socialmultiplication.event.EventDispatcher;
import microservice.book.socialmultiplication.event.MultiplicationSolvedEvent;
import microservice.book.socialmultiplication.repository.MultiplicationResultAttemptRepository;
import microservice.book.socialmultiplication.repository.UserRepository;
import microservice.book.socialmultiplication.service.impl.MultiplicationServiceImpl;
import org.apache.coyote.http11.Constants;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.eq;

/**
 * Created by Administrator on 2/20/2018.
 */
public class MultiplicationServiceImplTest {

    private MultiplicationService service;

    @Mock
    private RandomGeneratorService randomGeneratorService;

    @Mock
    private MultiplicationResultAttemptRepository attemptRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EventDispatcher eventDispatcher;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new MultiplicationServiceImpl(randomGeneratorService, attemptRepository, userRepository, eventDispatcher);
    }

    @Test
    public void createRandomMultiplicationTest() {
        given(randomGeneratorService.generateRandomFactor()).willReturn(50, 30);
        Multiplication multiplication = service.createRandomMultiplication();

        assertThat(multiplication.getFactorA()).isEqualTo(50);
        assertThat(multiplication.getFactorB()).isEqualTo(30);
    }

    @Test
    public void checkCorrectAttemptTest() {

        Multiplication multiplication = new Multiplication(50, 60);
        User user = new User("john_doe");
        MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3000, false);
        MultiplicationResultAttempt verifiedAttempt = new MultiplicationResultAttempt(user, multiplication, 3000, true);
        MultiplicationSolvedEvent event = new MultiplicationSolvedEvent(attempt.getId(), user.getId(), true);
        given(userRepository.findByAlias("john_doe")).willReturn(Optional.empty());

        boolean attempResult = service.checkAttempt(attempt);
        assertThat(attempResult).isTrue();
        Mockito.verify(attemptRepository).save(verifiedAttempt);
        Mockito.verify(eventDispatcher).send(eq(event));
    }

    @Test
    public void retrieveStatsTest() {

        Multiplication multiplication = new Multiplication(50, 60);
        User user = new User("john_doe");
        MultiplicationResultAttempt attempt1 = new MultiplicationResultAttempt(user, multiplication, 3010, false);
        MultiplicationResultAttempt attempt2 = new MultiplicationResultAttempt(user, multiplication, 3051, false);
        MultiplicationSolvedEvent event = new MultiplicationSolvedEvent(attempt1.getId(), user.getId(), false);
        List<MultiplicationResultAttempt> latestAttempts = Lists.newArrayList(attempt1, attempt2);
        given(userRepository.findByAlias("john_doe")).willReturn(Optional.empty());
        given(attemptRepository.findTop5ByUserAliasOrderByIdDesc("john_doe")).willReturn(latestAttempts);
        List<MultiplicationResultAttempt> latestAttemptsResult = service.getStatsForTheUser("john_doe");

        // then
        assertThat(latestAttemptsResult).isEqualTo (latestAttempts);

    }

    @Test
    public void checkWrongAttemptTest() {
        Multiplication multiplication = new Multiplication(50, 60);
        User user = new User("john_doe");
        MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3010, false);
        given(userRepository.findByAlias("john_doe")).willReturn(Optional.empty());
        boolean attemptResult = service.checkAttempt(attempt);
        assertThat(attemptResult).isFalse();
        Mockito.verify(attemptRepository).save(attempt);
    }
    }
