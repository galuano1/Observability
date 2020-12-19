package com.galuano1.webtest;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
public class DemoController {

    @RequestMapping("/")
    public String respond(HttpServletRequest request) {
        System.out.println(request.getMethod() + " API called at:" + new Date());
        return "Method:" + request.getMethod() ;
    }
}
