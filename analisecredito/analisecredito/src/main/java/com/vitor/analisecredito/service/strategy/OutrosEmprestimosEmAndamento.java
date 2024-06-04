package com.vitor.analisecredito.service.strategy;

import com.vitor.analisecredito.service.CaculoPonto;
import com.vitor.analisecredito.entity.Proposta;
import org.springframework.stereotype.Component;

import java.util.Random;
@Component
public class OutrosEmprestimosEmAndamento implements CaculoPonto
{
    @Override
    public int calcular(Proposta proposta) {
        return outrosEmprestimosEmAndamento() ? 0 : 80;
    }

    private boolean outrosEmprestimosEmAndamento() {
        return new Random().nextBoolean();
    }
}
