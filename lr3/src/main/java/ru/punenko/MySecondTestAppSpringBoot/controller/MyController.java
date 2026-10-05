package ru.punenko.MySecondTestAppSpringBoot.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import ru.punenko.MySecondTestAppSpringBoot.service.ModifyResponseService;
import ru.punenko.MySecondTestAppSpringBoot.service.ValidationService;
import ru.punenko.MySecondTestAppSpringBoot.util.DateTimeUtil;

import java.util.Date;
import java.util.Objects;

@Slf4j
@RestController
public class MyController {

    private final ValidationService validationService;
    private final ModifyResponseService modifyResponseService;

    @Autowired
    public MyController(ValidationService validationService,
                        @Qualifier("ModifySystemTimeResponseService") ModifyResponseService modifyResponseService) {
        this.validationService = validationService;
        this.modifyResponseService = modifyResponseService;
    }

    @PostMapping(value = "/feedback")
    public ResponseEntity<Response> feedback(@Valid @RequestBody Request request,
                                             BindingResult bindingResult) {
        log.info("request: {}", request);

        Response response = Response.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .systemTime(DateTimeUtil.getCustomFormat().format(new Date()))
                .code(Codes.SUCCESS)
                .errorCode(ErrorCodes.EMPTY)
                .errorMessage(ErrorMessages.EMPTY)
                .build();
        log.info("Response created: {}", response);

        try {
            validationService.isValid(bindingResult);
            if (Objects.equals(request.getUid(), "123")) {
                log.error("Unsupported uid: {}", request.getUid());
                throw new UnsupportedCodeException("Unsupported uid");
            }
        } catch (ValidationFailedException e) {
            log.error("Validation failed: {}", e.getMessage());
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.VALIDATION_EXCEPTION);
            response.setErrorMessage(ErrorMessages.VALIDATION);
            log.info("Response changed and sent with HTTP 400: {}", response);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (UnsupportedCodeException e) {
            log.error("Unsupported code: {}", e.getMessage());
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.UNSUPPORTED_EXCEPTION);
            response.setErrorMessage(ErrorMessages.UNSUPPORTED);
            log.info("Response changed and sent with HTTP 400: {}", response);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Unexpected error", e);
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.UNKNOWN_EXCEPTION);
            response.setErrorMessage(ErrorMessages.UNKNOWN);
            log.info("Response changed and sent with HTTP 500: {}", response);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        modifyResponseService.modify(response);
        Response modifiedResponse = modifyResponseService.modify(response);
        log.info("Response sent with HTTP 200: {}", modifiedResponse);
        return new ResponseEntity<>(modifiedResponse, HttpStatus.OK);
    }
}
