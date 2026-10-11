package ru.punenko.MyThirdTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import ru.punenko.MyThirdTestAppSpringBoot.model.Response;

@Service
public interface ModifyResponseService {
    Response modify(Response response);
}
