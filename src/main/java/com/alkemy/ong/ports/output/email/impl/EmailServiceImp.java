package com.alkemy.ong.ports.output.email.impl;

import com.alkemy.ong.ports.output.email.EmailService;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class EmailServiceImp implements EmailService {

    private static final String NO_REPLY_SOMOSMAS_ORG = "no-reply@somosmas.org";

    private SendGrid sendGridClient;

    @Value("${email.sendgrid.template}")
    private String templateId;

    @Autowired
    public void SendGridEmailService(SendGrid sendGridClient) {

        this.sendGridClient = sendGridClient;
    }

    @Override
    public void sendText(String to) {

        Response response = sendEmail(to);
    }

    @Override
    public void sendHTML(String to) {

        Response response = sendEmail(to);
    }

    private Response sendEmail(String to) {

        Personalization personalization= new Personalization();
        personalization.addTo(new Email(to));
        Mail mail = new Mail();
        mail.setFrom(new Email(NO_REPLY_SOMOSMAS_ORG));
        mail.addPersonalization(personalization);
        mail.setReplyTo(new Email(NO_REPLY_SOMOSMAS_ORG));
        Request request = new Request();
        Response response;
        mail.setTemplateId(templateId);

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            response = this.sendGridClient.api(request);

        } catch (IOException ex) {
            log.error("Error sending email", ex);
            throw new RuntimeException(ex);
        }
        return response;
    }
}
