package com.pieropan.notifacao.listener;

import com.pieropan.notifacao.constante.MensagemConstante;
import com.pieropan.notifacao.domain.Proposta;
import com.pieropan.notifacao.service.NotificacaoSnsService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PropostaPendenteListener {

    private final NotificacaoSnsService notificacaoSnsService;

    @RabbitListener(queues = "${rabbitmq.queue.proposta.pendente}")
    public void propostaPendente(Proposta proposta) {

        String mensagem = String.format(MensagemConstante.PROPOSTA_EM_ANALISE, proposta.getUsuario().getNome());
        notificacaoSnsService.notificar(mensagem);


    }
}
