package microservice.book.socialmultiplication.service.impl;

import lombok.extern.slf4j.Slf4j;
import microservice.book.socialmultiplication.domain.Multiplication;
import microservice.book.socialmultiplication.domain.MultiplicationResultAttempt;
import microservice.book.socialmultiplication.domain.User;
import microservice.book.socialmultiplication.event.EventDispatcher;
import microservice.book.socialmultiplication.event.MultiplicationSolvedEvent;
import microservice.book.socialmultiplication.repository.MultiplicationResultAttemptRepository;
import microservice.book.socialmultiplication.repository.UserRepository;
import microservice.book.socialmultiplication.service.MultiplicationService;
import microservice.book.socialmultiplication.service.RandomGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Created by Administrator on 2/19/2018.
 */

@Service
@Slf4j
public class MultiplicationServiceImpl implements MultiplicationService {

    private RandomGeneratorService randomGeneratorService;
    private MultiplicationResultAttemptRepository attemptRepository;
    private UserRepository userRepository;
    private EventDispatcher eventDispatcher;

    @Autowired
    public MultiplicationServiceImpl(RandomGeneratorService randomGeneratorService, MultiplicationResultAttemptRepository attemptRepository, UserRepository userRepository, EventDispatcher eventDispatcher) {
        this.randomGeneratorService = randomGeneratorService;
        this.attemptRepository = attemptRepository;
        this.userRepository = userRepository;
        this.eventDispatcher = eventDispatcher;
    }

    @Override
    public List<MultiplicationResultAttempt> getStatsForTheUser(String userAlias) {
        return attemptRepository.findTop5ByUserAliasOrderByIdDesc(userAlias);
    }

    @Override
    public Multiplication createRandomMultiplication() {
        int factorA = randomGeneratorService.generateRandomFactor();
        int factorB = randomGeneratorService.generateRandomFactor();
        return new Multiplication(factorA, factorB);
    }

    @Transactional
    @Override
    public boolean checkAttempt(MultiplicationResultAttempt attempt) {
        Optional<User> user =  userRepository.findByAlias(attempt.getUser().getAlias());

        boolean correct = attempt.getResultAttempt() == attempt.getMultiplication().getFactorA() * attempt.getMultiplication().getFactorB();

        Assert.isTrue(!attempt.isCorrect(), "You can't send an attempt marked as correct");

        MultiplicationResultAttempt checkedAttempt = new MultiplicationResultAttempt(user.orElse(attempt.getUser()), attempt.getMultiplication(), attempt.getResultAttempt(), correct);
        attemptRepository.save(checkedAttempt);
        eventDispatcher.send(new MultiplicationSolvedEvent(checkedAttempt.getId(), checkedAttempt.getUser().getId(), checkedAttempt.isCorrect()));
        return correct;
    }

    @Transactional
    @Override
    public MultiplicationResultAttempt getOne(Long id) {
        return attemptRepository.findOne(id);
    }
}
