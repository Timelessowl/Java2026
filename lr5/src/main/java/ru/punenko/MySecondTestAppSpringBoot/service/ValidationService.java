package ru.punenko.MySecondTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import ru.punenko.MySecondTestAppSpringBoot.exception.UnsupportedCodeException;
import ru.punenko.MySecondTestAppSpringBoot.exception.ValidationFailedException;
import ru.punenko.MySecondTestAppSpringBoot.model.Request;

@Service
public interface ValidationService {
    void validate(Request request, BindingResult bindingResult)
            throws ValidationFailedException, UnsupportedCodeException;
}
