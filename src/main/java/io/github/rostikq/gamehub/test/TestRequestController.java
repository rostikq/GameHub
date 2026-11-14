package io.github.rostikq.gamehub.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/test")
public class TestRequestController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/helloworld/{user}")
    private String helloworld(@PathVariable("user") String user){
        logger.info("Received helloworld request for {}", user);
        return "Hello!" + user;
    }
}
