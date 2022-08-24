package com.alkemy.ong.ports.output.email;

public interface EmailService {
    void sendText(String to);
    void sendHTML(String to);
}
