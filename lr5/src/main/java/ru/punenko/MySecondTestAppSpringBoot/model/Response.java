package ru.punenko.MySecondTestAppSpringBoot.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Response {
    /** Уникальный идентификатор сообщения */
    private String uid;

    /** Уникальный идентификатор операции */
    private String operationUid;

    /** Время создания ответа */
    private String systemTime;

    /** Код результата обработки */
    private Codes code;

    /** Годовая премия */
    private Double annualBonus;

    /** Код ошибки */
    private ErrorCodes errorCode;

    /** Сообщение об ошибке */
    private ErrorMessages errorMessage;
}
