package org.crishof.mailsendersv.controller;


import org.crishof.mailsendersv.model.MailStructure;
import org.crishof.mailsendersv.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mail")
public class MailController {

    @Autowired
    private MailService mailService;

    @PostMapping("/send/{mail}")
    public String sendMail(@PathVariable String mail, @RequestBody MailStructure mailStructure) {

        System.out.println("mail = " + mail);
        mailService.sendMail(mail, mailStructure);
        return "success";
    }
}
