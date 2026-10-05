package ru.punenko.MySecondTestAppSpringBoot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import ru.punenko.MySecondTestAppSpringBoot.exception.ValidationFailedException;

@Slf4j
@Service
public class RequestValidationService implements ValidationService {
    @Override
    public void isValid(BindingResult bindingResult) throws ValidationFailedException {
        log.info("Request validation started");
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error ->
                    log.error("Request validation error: {}", error));
            log.error("Throwing ValidationFailedException");
            throw new ValidationFailedException(bindingResult.getFieldError().toString());
        }
        log.info("Request validation completed successfully");
    }
}
