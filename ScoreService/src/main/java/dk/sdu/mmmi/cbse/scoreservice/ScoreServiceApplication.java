package dk.sdu.mmmi.cbse.scoreservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;


@SpringBootApplication
@RestController
public class ScoreServiceApplication {

    private AtomicInteger totalScore = new AtomicInteger(0);

    public static void main(String[] args) {
        SpringApplication.run(ScoreServiceApplication.class, args);
    }

    @GetMapping("/score")
    public int getScore() {
        return totalScore.get();
    }

    @PutMapping("/score")
    public int updateScore(@RequestParam int score) {
        return totalScore.addAndGet(score);
    }

}
