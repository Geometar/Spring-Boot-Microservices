package microservices.book.gamification.event;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Administrator on 3/1/2018.
 */

@Data
public class MultiplicationSolvedEvent implements Serializable{

    private final Long multiplicationResultAttemptId;
    private final Long userId;
    private final boolean correct;

}

