package com.vitor.analisecredito.service.strategy;

import com.vitor.analisecredito.service.CaculoPonto;
import com.vitor.analisecredito.entity.Proposta;
import org.springframework.stereotype.Component;

@Component
public class PrazoPagamentoInferiorDezAnos implements CaculoPonto {
    @Override
    public int calcular(Proposta proposta) {
        return proposta.getPrazoPagamento() <= 120 ? 80 : 0;
    }
}
