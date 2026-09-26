package ru.punenko.MySecondTestAppSpringBoot.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Request {
    @NotBlank(message = "uid is required")
    @Size(max = 32, message = "uid must contain no more than 32 characters")
    private String uid;

    @NotBlank(message = "operationUid is required")
    @Size(max = 32, message = "operationUid must contain no more than 32 characters")
    private String operationUid;

    private String systemName;

    @NotBlank(message = "systemTime is required")
    private String systemTime;

    private String source;

    @Min(value = 1, message = "communicationId must be at least 1")
    @Max(value = 100000, message = "communicationId must be no more than 100000")
    private int communicationId;

    private int templateId;
    private int productCode;
    private int smsCode;
}
