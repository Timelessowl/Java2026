package ru.punenko.MySecondTestAppSpringBoot.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Request {
    /**
     * Уникальный идентификатор сообщения
     */
    @NotBlank(message = "UID не может быть пустым")
    private String uid;

    /** Уникальный идентификатор операции */
    private String operationUid;

    /** Имя системы отправителя */
    private Systems systemName;

    /** Время создания сообщения */
    private String systemTime;

    /** Наименование ресурса */
    private String source;

    /** Должность сотрудника */
    private Positions position;

    /** Оклад сотрудника */
    private Double salary;

    /** Множитель премии */
    private Double bonus;

    /** Количество отработанных дней */
    private Integer workDays;

    /** Уникальный идентификатор коммуникации */
    private Integer communicationId;

    /** Уникальный идентификатор шаблона */
    private Integer templateId;

    /** Код продукта */
    private Integer productCode;

    /** СМС-код */
    private Integer smsCode;

    @Override
    public String toString() {
        return "{" +
                "uid='" + uid + '\'' +
                ", operationUid='" + operationUid + '\'' +
                ", systemName='" + systemName + '\'' +
                ", systemTime='" + systemTime + '\'' +
                ", source='" + source + '\'' +
                ", communicationId=" + communicationId +
                ", templateId=" + templateId +
                ", productCode=" + productCode +
                ", smsCode=" + smsCode +
                '}';
    }
}
