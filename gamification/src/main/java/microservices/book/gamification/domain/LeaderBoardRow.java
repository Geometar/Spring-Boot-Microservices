package microservices.book.gamification.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Created by Administrator on 2/28/2018.
 */

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public final class LeaderBoardRow {

    private final Long userID;
    private final Long totalScore;

    public LeaderBoardRow() {
        this(0L, 0L);
    }
}
