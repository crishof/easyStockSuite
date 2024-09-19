package org.crishof.mailsendersv.controller;


import lombok.RequiredArgsConstructor;
import org.crishof.mailsendersv.model.MailStructure;
import org.crishof.mailsendersv.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class MailController {

    private final MailService mailService;

    @PostMapping("/send/{mail}")
    public String sendMail(@PathVariable String mail, @RequestBody MailStructure mailStructure) {
        mailService.sendMail(mail, mailStructure);
        return "success";
    }
}
