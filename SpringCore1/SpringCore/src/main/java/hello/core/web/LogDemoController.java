package hello.core.web;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller @RequiredArgsConstructor
public class LogDemoController {

    private final LogDemoService logDemoService;

    private final MyLogger logger;

    @ResponseBody
    @RequestMapping(value = "/log-demo")
    public String logDemo(HttpServletRequest request) {
        String requestUrl = request.getRequestURL().toString();

        System.out.println("logger.class = " + logger.getClass());  // 껍데기 MyLogger  (Proxy)
        logger.setRequestURL(requestUrl);

        logger.log("controller test");
        logDemoService.logic("testId");
        return "OK";
    }

}
