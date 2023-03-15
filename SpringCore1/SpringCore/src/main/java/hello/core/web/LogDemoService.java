package hello.core.web;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class LogDemoService {

    private final MyLogger logger;

    public void logic(String id) {
        logger.log("service id = " + id);
    }
}
