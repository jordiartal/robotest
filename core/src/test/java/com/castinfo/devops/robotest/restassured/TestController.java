package com.castinfo.devops.robotest.restassured;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    public static final String HELLO_WORLD = "Hello World";

    @RequestMapping(value = "/ping", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String ping() {
        return TestController.HELLO_WORLD;
    }

    @RequestMapping(value = "/echo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String echo(@RequestParam("echo") final String echo) {
        return echo;
    }

    @RequestMapping(value = "/jackson", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody JacksonPojo jackson() {
        JacksonPojo pojo = new JacksonPojo();
        pojo.setEcho(TestController.HELLO_WORLD);
        return pojo;
    }

    @RequestMapping(value = "/jacksonEcho", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody JacksonPojo jacksonEcho(@RequestParam("echo") final String echo) {
        JacksonPojo pojo = new JacksonPojo();
        pojo.setEcho(echo);
        return pojo;
    }

    @RequestMapping(value = "/xmljaxb", method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody JaxbPojoType xmljaxb() {
        JaxbPojoType pojo = new JaxbPojoType();
        pojo.setEcho(TestController.HELLO_WORLD);
        return pojo;
    }

    @RequestMapping(value = "/xmljaxbEcho", method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody JaxbPojoType xmljaxbEcho(@RequestParam("echo") final String echo) {
        JaxbPojoType pojo = new JaxbPojoType();
        pojo.setEcho(echo);
        return pojo;
    }

}