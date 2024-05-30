package com.pieropan.propostaapp.service;

import com.pieropan.propostaapp.dto.PropostaRequestDto;
import com.pieropan.propostaapp.dto.PropostaResponseDto;
import com.pieropan.propostaapp.entity.Proposta;
import com.pieropan.propostaapp.mapper.PropostaMapper;
import com.pieropan.propostaapp.repository.PropostaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PropostaService {

    private final PropostaRepository propostaRepository;
    private final PropostaMapper mapper;
    private final NotificacaoService notificacaoService;

    @Value("${rabbitmq.propostapendente.exchange}")
    private final String exchange;

    public PropostaResponseDto criar(PropostaRequestDto requestDto) {

        var proposta = mapper.convertDtoToProposta(requestDto);
        proposta = propostaRepository.save(proposta);

        var propostaResponse = mapper.propostaToPropostaResponseDto(proposta);
        notificarRabbitMq(proposta);

        return propostaResponse;
    }

    private void notificarRabbitMq(Proposta proposta) {
        try {
            notificacaoService.notificar(proposta, exchange);
        } catch (RuntimeException ex) {
            proposta.setIntegrada(false);
            propostaRepository.save(proposta);
        }
    }

    public List<PropostaResponseDto> obterPropostas() {
        return mapper.propostasToPropostaResponseDto(propostaRepository.findAll());
    }
}
