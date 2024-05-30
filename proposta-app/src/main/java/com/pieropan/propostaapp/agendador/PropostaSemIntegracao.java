package com.pieropan.propostaapp.agendador;

import com.pieropan.propostaapp.entity.Proposta;
import com.pieropan.propostaapp.repository.PropostaRepository;
import com.pieropan.propostaapp.service.NotificacaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Log4j2
public class PropostaSemIntegracao {

    private final PropostaRepository propostaRepository;
    private final NotificacaoService notificacaoService;
    @Value("${rabbitmq.propostapendente.exchange}")
    private final String exchange;

    @Scheduled(fixedDelay = 10, timeUnit = TimeUnit.SECONDS)
    public void buscarPropostaSemIntegracao() {

        propostaRepository.findAllByIntegradaIsFalse().forEach(proposta -> {

            try {
                notificacaoService.notificar(proposta, exchange);
                atualizarProposta(proposta);
            } catch (RuntimeException ex) {
                log.error(ex.getMessage());
            }
        });


    }
    private void atualizarProposta(Proposta proposta) {
        proposta.setIntegrada(true);
        propostaRepository.save(proposta);
    }
}
