package com.example.curso.service.impl;

import com.example.curso.model.entity.Lancamento;
import com.example.curso.model.entity.StatusLancamento;
import com.example.curso.model.repository.LancamentoRepository;
import com.example.curso.model.repository.LancamentoRepositoryTest;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class LancamentoServiceTest {

    @MockBean
    LancamentoRepository lancamentoRepository;

    @SpyBean
    LancamentoServiceImpl service;

    @Test
    public void deveSalvarUmLancamento(){
        Lancamento lancamentoASalvar = LancamentoRepositoryTest.getLancamento();
        Mockito.doNothing().when(service).validar(lancamentoASalvar);

        Lancamento lancamentoSalvo = LancamentoRepositoryTest.getLancamento();
        lancamentoSalvo.setId(1l);
        lancamentoSalvo.setStatus(StatusLancamento.PENDENTE);
        Mockito.when(lancamentoRepository.save(lancamentoASalvar)).thenReturn(lancamentoSalvo);

        Lancamento lancamento = service.salvar(lancamentoASalvar);

        Assertions.assertThat(lancamento.getId()).isEqualTo(lancamentoSalvo.getId());
        Assertions.assertThat(lancamento.getStatus()).isEqualTo(StatusLancamento.PENDENTE);
    }
}
