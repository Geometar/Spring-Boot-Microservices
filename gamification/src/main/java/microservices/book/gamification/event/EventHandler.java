package microservices.book.gamification.event;

import lombok.extern.slf4j.Slf4j;
import microservices.book.gamification.service.GameService;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 3/1/2018.
 */

@Slf4j
@Component
public class EventHandler {

    private GameService gameService;

    @Autowired
    public EventHandler(GameService gameService) {
        this.gameService = gameService;
    }

    @RabbitListener(queues = "${multiplication.queue}")
    void handleMultiplicationSolved(final MultiplicationSolvedEvent event) {

        log.info("Multiplication Solved Event received: {}", event.getMultiplicationResultAttemptId());
        try{
            gameService.newAttemptForUser(event.getUserId(), event.getMultiplicationResultAttemptId(), event.isCorrect());
        } catch (final Exception ex){
            log.error("Error when trying to process MultiplicationSolvedEvent", ex);
            throw new AmqpRejectAndDontRequeueException(ex);
        }
    }
}
