package com.alkemy.ong.ports.output.email.impl;

import com.alkemy.ong.ports.output.email.EmailService;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailServiceImp implements EmailService {


   private SendGrid sendGridClient;
   private static final String NO_REPLY_SOMOSMAS_ORG = "no-reply@somosmas.org";
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


    private Response sendEmail(String from, String to, String subject, Content content) {


        Mail mail = new Mail(new Email(from), subject, new Email(to), content);
        mail.setReplyTo(new Email(NO_REPLY_SOMOSMAS_ORG));
        Request request = new Request();
        Response response = null;


        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            response=this.sendGridClient.api(request);


        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return response;


    }


}
