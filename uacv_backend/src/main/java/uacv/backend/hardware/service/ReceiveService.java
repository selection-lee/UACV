package uacv.backend.hardware.service;

import org.springframework.amqp.core.Message;

public interface ReceiveService {

    void receiveMessage(Message message);
}
