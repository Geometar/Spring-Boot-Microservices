package microservice.book.socialmultiplication.controller;

import lombok.extern.slf4j.Slf4j;
import microservice.book.socialmultiplication.domain.Multiplication;
import microservice.book.socialmultiplication.service.MultiplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2/20/2018.
 */

@Slf4j
@RestController
@RequestMapping("/multiplications")
final class MultiplicationController {

    private final MultiplicationService multiplicationService;
    private final int serverPort;

    @Autowired
    public  MultiplicationController(final MultiplicationService multiplicationService, @Value("${server.port}") int serverPort) {
        this.multiplicationService = multiplicationService;
        this.serverPort = serverPort;
    }

    @GetMapping("/random")
    Multiplication getRandomMultiplication() {
        log.info("Generating a random multiplication from server @ {}", this.serverPort);
        return multiplicationService.createRandomMultiplication();
    }
}

