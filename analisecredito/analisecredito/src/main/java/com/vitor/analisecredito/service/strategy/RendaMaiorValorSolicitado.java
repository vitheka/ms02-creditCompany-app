package com.vitor.analisecredito.service.strategy;

import com.vitor.analisecredito.service.CaculoPonto;
import com.vitor.analisecredito.entity.Proposta;
import org.springframework.stereotype.Component;

@Component
public class RendaMaiorValorSolicitado implements CaculoPonto {
    @Override
    public int calcular(Proposta proposta) {
        return rendaMaiorValorSolicitado(proposta) ? 100 : 0;
    }

    private boolean rendaMaiorValorSolicitado(Proposta proposta) {
        return proposta.getUsuario().getRenda() > proposta.getValorSolicitado();

    }
}
