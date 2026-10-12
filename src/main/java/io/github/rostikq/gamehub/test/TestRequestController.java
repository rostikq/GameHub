package io.github.rostikq.gamehub.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/test")
public class TestRequestController {
    @GetMapping("/helloworld/{user}")
    private String helloworld(@PathVariable("user") String user){
        return "Hello!" + user;
    }
}
