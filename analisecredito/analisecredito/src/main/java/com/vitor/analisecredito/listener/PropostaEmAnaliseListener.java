package com.vitor.analisecredito.listener;

import com.vitor.analisecredito.entity.Proposta;
import com.vitor.analisecredito.service.AnaliseCreditoService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class PropostaEmAnaliseListener {

    private final AnaliseCreditoService analiseCreditoService;

    @RabbitListener(queues = "${rabbitmq.propostapendente.queue}")
    public void propostaEmAnalise(Proposta proposta) {
        analiseCreditoService.analisar(proposta);
    }
}
