package ru.education.springmvc;

import java.time.OffsetDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(produces = MediaType.TEXT_PLAIN_VALUE + ";charset=UTF-8")
public class TaskController {

    private static final int MAX_RESULT_LENGTH = 1_000_000;

    @GetMapping("/about")
    public String about() {
        return "Пуненко К.В., группа РИМ 250950";
    }

    @GetMapping("/time")
    public String time() {
        return OffsetDateTime.now().toString();
    }

    @GetMapping("/repeat")
    public String repeat(@RequestParam(name = "text") String text,
                         @RequestParam(name = "count") int count) {
        if (count < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "count must be non-negative");
        }
        if ((long) text.length() * count > MAX_RESULT_LENGTH) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Result must not exceed " + MAX_RESULT_LENGTH + " UTF-16 code units");
        }
        return text.repeat(count);
    }
}
