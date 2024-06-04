package com.vitor.analisecredito.service;


import com.vitor.analisecredito.entity.Proposta;
import com.vitor.analisecredito.exception.StrategyException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AnaliseCreditoServiceTest {

    public static final String ERROS_NOS_STRATEGY = "Erros nos strategy";
    @Mock
    private CaculoPonto caculoPonto;

    @Mock
    private NotificacaoRabbitService notificacaoRabbitService;


    @Test
    @Order(1)
    void analisar_WhenSuccessful() {

        // Definindo o comportamento do mock
        when(caculoPonto.calcular(any(Proposta.class))).thenReturn(500);

        // Criação da lista com o mock
        List<CaculoPonto> caculoPontoList = Arrays.asList(caculoPonto, caculoPonto);

        // Criação da proposta
        Proposta proposta = new Proposta();

        // Criação da instância da classe a ser testada
        AnaliseCreditoService service = new AnaliseCreditoService(caculoPontoList, notificacaoRabbitService);

        // Execução do método a ser testado
        service.analisar(proposta);

        // Verificações
        assertTrue(proposta.getAprovada());
    }

    @Test
    @Order(2)
    void analisar_ReturnStrategyException_WhenErrorFound() {

        // Definindo o comportamento do mock
        when(caculoPonto.calcular(any(Proposta.class))).thenThrow(new StrategyException(ERROS_NOS_STRATEGY));

        // Criação da lista com o mock
        List<CaculoPonto> caculoPontoList = Arrays.asList(caculoPonto, caculoPonto);

        // Criação da proposta
        Proposta proposta = new Proposta();

        // Criação da instância da classe a ser testada
        AnaliseCreditoService service = new AnaliseCreditoService(caculoPontoList, notificacaoRabbitService);

        service.analisar(proposta);

        assertFalse(proposta.getAprovada());
        assertEquals(ERROS_NOS_STRATEGY, proposta.getObservacao());

    }


}