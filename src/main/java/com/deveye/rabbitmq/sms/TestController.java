package com.deveye.rabbitmq.sms;

import com.deveye.rabbitmq.sms.service.TestService;
import com.deveye.rabbitmq.sms.utils.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private TestService testService;


    @RequestMapping("/send")
    public Object send(Mail mail) {
        return testService.send(mail);
    }


}
