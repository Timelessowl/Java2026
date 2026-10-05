package ru.punenko.MySecondTestAppSpringBoot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.punenko.MySecondTestAppSpringBoot.model.Response;
import java.util.Date;
import ru.punenko.MySecondTestAppSpringBoot.util.DateTimeUtil;

@Slf4j
@Service
@Qualifier("ModifySystemTimeResponseService")
public class ModifySystemTimeResponseService implements ModifyResponseService {
    @Override
    public Response modify(Response response) {
        String previous = response.getSystemTime();
        response.setSystemTime(DateTimeUtil.getCustomFormat().format(new Date()));
        log.info("Response uid={}: systemTime changed from {} to {}",
                response.getUid(), previous, response.getSystemTime());
        return response;
    }
}
