package ru.punenko.MySecondTestAppSpringBoot.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.punenko.MySecondTestAppSpringBoot.exception.UnsupportedCodeException;
import ru.punenko.MySecondTestAppSpringBoot.exception.ValidationFailedException;
import ru.punenko.MySecondTestAppSpringBoot.model.Codes;
import ru.punenko.MySecondTestAppSpringBoot.model.ErrorCodes;
import ru.punenko.MySecondTestAppSpringBoot.model.ErrorMessages;
import ru.punenko.MySecondTestAppSpringBoot.model.Request;
import ru.punenko.MySecondTestAppSpringBoot.model.Response;
import ru.punenko.MySecondTestAppSpringBoot.service.AnnualBonusService;
import ru.punenko.MySecondTestAppSpringBoot.service.ValidationService;
import ru.punenko.MySecondTestAppSpringBoot.util.DateTimeUtil;

import java.util.Date;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MyController {

    private final ValidationService validationService;
    private final AnnualBonusService annualBonusService;

    @PostMapping("/feedback")
    public ResponseEntity<Response> feedback(@Valid @RequestBody Request request,
                                             BindingResult bindingResult) {
        log.info("Processing request uid={}", request.getUid());
        Response response = Response.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .systemTime(DateTimeUtil.getCustomFormat().format(new Date()))
                .code(Codes.SUCCESS)
                .errorCode(ErrorCodes.EMPTY)
                .errorMessage(ErrorMessages.EMPTY)
                .build();

        try {
            validationService.validate(request, bindingResult);
            if (request.getPosition() != null) {
                response.setAnnualBonus(annualBonusService.calculate(request.getPosition(),
                        request.getSalary(), request.getBonus(), request.getWorkDays()));
            }
            return ResponseEntity.ok(response);
        } catch (ValidationFailedException e) {
            log.warn("Validation failed for uid={}: {}", request.getUid(), e.getMessage());
            return failure(response, HttpStatus.BAD_REQUEST, ErrorCodes.VALIDATION_EXCEPTION, ErrorMessages.VALIDATION);
        } catch (UnsupportedCodeException e) {
            log.warn("Unsupported uid={}", request.getUid());
            return failure(response, HttpStatus.BAD_REQUEST, ErrorCodes.UNSUPPORTED_EXCEPTION, ErrorMessages.UNSUPPORTED);
        } catch (Exception e) {
            log.error("Unexpected error", e);
            return failure(response, HttpStatus.INTERNAL_SERVER_ERROR, ErrorCodes.UNKNOWN_EXCEPTION, ErrorMessages.UNKNOWN);
        }
    }

    private ResponseEntity<Response> failure(Response response, HttpStatus status,
                                             ErrorCodes errorCode, ErrorMessages errorMessage) {
        response.setCode(Codes.FAILED);
        response.setErrorCode(errorCode);
        response.setErrorMessage(errorMessage);
        return ResponseEntity.status(status).body(response);
    }
}
