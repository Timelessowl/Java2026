package ru.punenko.MySecondTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import ru.punenko.MySecondTestAppSpringBoot.exception.UnsupportedCodeException;
import ru.punenko.MySecondTestAppSpringBoot.exception.ValidationFailedException;
import ru.punenko.MySecondTestAppSpringBoot.model.Request;

@Service
public class RequestValidationService implements ValidationService {
    @Override
    public void validate(Request request, BindingResult bindingResult)
            throws ValidationFailedException, UnsupportedCodeException {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        if (request.getPosition() == null && request.getSalary() == null
                && request.getBonus() == null && request.getWorkDays() == null) {
            return;
        }
        if (request.getPosition() == null || request.getSalary() == null
                || request.getBonus() == null || request.getWorkDays() == null) {
            throw new ValidationFailedException("position, salary, bonus and workDays must be provided together");
        }
        if (request.getWorkDays() <= 0) {
            throw new ValidationFailedException("workDays must be greater than zero");
        }
    }
}
