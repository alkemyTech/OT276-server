package com.alkemy.ong.ports.output.email.impl;

import com.alkemy.ong.core.model.Organization;
import com.alkemy.ong.ports.output.email.EmailService;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
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
    public void sendText(String from, String to, String subject, String body) {

        Response response = sendEmail(from, to, subject, new Content("text/plain", body));
    }

    @Override
    public void sendHTML(String from, String to, String subject, String body) {

        Response response = sendEmail(from, to, subject, new Content("text/html", body));
    }

    public void sendWelcomEmail(Organization organization, String to) {

        Response response = sendEmail(organization.getEmail(), to);
    }

    private Response sendEmail(String from, String to) {

        Personalization personalization = new Personalization();
        personalization.addTo(new Email(to));
        Mail mail = new Mail();
        mail.setFrom(new Email(from));
        mail.setReplyTo(new Email(NO_REPLY_SOMOSMAS_ORG));
        Request request = new Request();
        Response response;
        mail.setTemplateId(templateId);
        mail.addPersonalization(personalization);

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

    private Response sendEmail(String from, String to, String subject, Content content) {
        Mail mail = new Mail(new Email(from), subject, new Email(to), content);
        mail.setReplyTo(new Email(NO_REPLY_SOMOSMAS_ORG));
        Request request = new Request();
        Response response;
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
