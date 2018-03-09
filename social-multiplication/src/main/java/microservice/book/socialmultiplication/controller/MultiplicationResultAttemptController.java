package microservice.book.socialmultiplication.controller;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import microservice.book.socialmultiplication.domain.MultiplicationResultAttempt;
import microservice.book.socialmultiplication.service.MultiplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Administrator on 2/20/2018.
 */

@Slf4j
@RestController
@RequestMapping("/results")
public class MultiplicationResultAttemptController {

    private final MultiplicationService multiplicationService;
    private final int serverPort;

    @Autowired
    public MultiplicationResultAttemptController(final MultiplicationService multiplicationService, @Value("${server.port}") int serverPort) {
        this.multiplicationService = multiplicationService;
        this.serverPort = serverPort;
    }

    @PostMapping
    ResponseEntity<MultiplicationResultAttempt> postResult(@RequestBody MultiplicationResultAttempt resultAttempt) {
        boolean isCorrect = multiplicationService.checkAttempt(resultAttempt);
        MultiplicationResultAttempt attemptCopy = new MultiplicationResultAttempt(resultAttempt.getUser(), resultAttempt.getMultiplication(), resultAttempt.getResultAttempt(), isCorrect);
        return ResponseEntity.ok(attemptCopy);
    }

    @GetMapping
    ResponseEntity<List<MultiplicationResultAttempt>> getStatistics(String alias) {
        return ResponseEntity.ok(multiplicationService.getStatsForTheUser(alias));
    }

    @GetMapping(value = "/{id}")
    ResponseEntity<MultiplicationResultAttempt> getMultiplicationResultAttempt(@PathVariable(value = "id") Long id) {
        log.info("Retrieving result {} from server @ {}", id, serverPort);
        MultiplicationResultAttempt resultAttempt = multiplicationService.getOne(id);
        return ResponseEntity.ok(resultAttempt);
    }
}
