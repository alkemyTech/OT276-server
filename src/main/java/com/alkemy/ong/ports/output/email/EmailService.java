package com.alkemy.ong.ports.output.email;

import com.alkemy.ong.core.model.Organization;

public interface EmailService {

    void sendText(String from, String to, String subject, String body);

    void sendHTML(String from, String to, String subject, String body);

    void sendWelcomeEmail(Organization organization, String to);
}
