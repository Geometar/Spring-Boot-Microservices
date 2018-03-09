package microservices.book.gamification.service;

import microservices.book.gamification.domain.GameStats;

/**
 * Created by Administrator on 2/28/2018.
 */
public interface GameService {

    GameStats newAttemptForUser(Long userId, Long attemptId, boolean correct);

    GameStats retrieveStatsForUser(Long userId);
}
