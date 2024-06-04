package com.vitor.analisecredito.service;

import com.vitor.analisecredito.CaculoPonto;
import com.vitor.analisecredito.StrategyException;
import com.vitor.analisecredito.entity.Proposta;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class AnaliseCreditoService {

    public final List<CaculoPonto> caculoPontoList;
    public final NotificacaoRabbitService notificacaoRabbitService;

    @Value("${rabbitmq.propostaconcluida.exchange}")
    private String exchangePropostaConcluida;

    public void analisar(Proposta proposta) {
        try {
            var aprovada = caculoPontoList.stream()
                    .mapToInt(impl -> impl.calcular(proposta)).sum() > 350;
            proposta.setAprovada(aprovada);
        } catch (StrategyException ex) {
            log.error("Erro finalização proposta {}", ex.getMessage());
            proposta.setAprovada(false);
            proposta.setObservacao(ex.getMessage());
        }
        notificacaoRabbitService.notificar(exchangePropostaConcluida, proposta);


    }
}
