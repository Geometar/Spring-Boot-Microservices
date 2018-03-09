package microservices.book.gamification.service;

import microservices.book.gamification.domain.LeaderBoardRow;

import java.util.List;

/**
 * Created by Administrator on 2/28/2018.
 */
public interface LeaderBoardService {

    List<LeaderBoardRow> getCurrentLeaderBoard();
}
