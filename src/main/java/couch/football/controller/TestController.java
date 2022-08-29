package couch.football.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/front")
    public String test() {
        return "test success!!";
    }

}
