package com.vitor.analisecredito.service.strategy;

import com.vitor.analisecredito.service.CaculoPonto;
import com.vitor.analisecredito.exception.StrategyException;
import com.vitor.analisecredito.entity.Proposta;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Random;

@Order(1)
@Component
public class NomeNegativadoImpl implements CaculoPonto {
    @Override
    public int calcular(Proposta proposta) {

        if (nomeNegativado()) {
            throw new StrategyException("Nome negativado");
        }

        return 100;
    }

    private boolean nomeNegativado() {
       return new Random().nextBoolean();
    }
}
