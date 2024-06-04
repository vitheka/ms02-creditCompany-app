package com.vitor.analisecredito.service;

import com.vitor.analisecredito.entity.Proposta;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificacaoRabbitService {

    private final RabbitTemplate rabbitTemplate;

    public void notificar(String exchange, Proposta proposta) {
        rabbitTemplate.convertAndSend(exchange, "", proposta);
    }
}
