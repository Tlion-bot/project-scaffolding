package com.base.test.common.service;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    public void sendMessageToEmail(String verifyCode, String email);
}
