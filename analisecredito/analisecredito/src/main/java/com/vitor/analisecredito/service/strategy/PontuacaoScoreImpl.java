package com.vitor.analisecredito.service.strategy;

import com.vitor.analisecredito.CaculoPonto;
import com.vitor.analisecredito.StrategyException;
import com.vitor.analisecredito.entity.Proposta;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Random;

@Order(2)
@Component
public class PontuacaoScoreImpl implements CaculoPonto {
    @Override
    public int calcular(Proposta proposta) {
        int score = score();

        if (score <= 200) {
            throw new StrategyException("Score abaixo");
        } else if (score <= 400) {
            return 150;
        } else if (score <= 600) {
            return 180;
        } else {
            return 220;
        }

    }

    private int score() {
        return new Random().nextInt(0, 1000);
    }
}
