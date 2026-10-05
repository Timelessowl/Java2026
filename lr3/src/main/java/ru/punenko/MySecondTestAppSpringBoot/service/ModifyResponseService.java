package ru.punenko.MySecondTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import ru.punenko.MySecondTestAppSpringBoot.model.Response;

@Service
public interface ModifyResponseService {
    Response modify(Response response);
}
