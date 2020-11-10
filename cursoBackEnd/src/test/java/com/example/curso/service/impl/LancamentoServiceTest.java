package com.example.curso.service.impl;

import com.example.curso.exception.RegraDeNegocioException;
import com.example.curso.model.entity.Lancamento;
import com.example.curso.model.entity.StatusLancamento;
import com.example.curso.model.repository.LancamentoRepository;
import com.example.curso.model.repository.LancamentoRepositoryTest;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Null;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

    /**
     * Verificando se lança uma Exception
     */
    @Test
    public void naoDeveSalvarUmLancamentoQuandoHouverErroDeValidacao(){
        // cenário
        Lancamento lancamentoASalvar = LancamentoRepositoryTest.getLancamento();
        Mockito.doThrow(RegraDeNegocioException.class).when(service).validar(lancamentoASalvar);

        Assertions.catchThrowableOfType(()-> service.salvar(lancamentoASalvar), RegraDeNegocioException.class);

        Mockito.verify(lancamentoRepository, Mockito.never()).save(lancamentoASalvar);
    }

    @Test
    public void deveAtualizarUmLancamento(){
        // cenário
        Lancamento lancamentoSalvo = LancamentoRepositoryTest.getLancamento();
        lancamentoSalvo.setId(1l);
        lancamentoSalvo.setStatus(StatusLancamento.PENDENTE);

        Mockito.doNothing().when(service).validar(lancamentoSalvo);

        Mockito.when(lancamentoRepository.save(lancamentoSalvo)).thenReturn(lancamentoSalvo);

        // execução
        service.salvar(lancamentoSalvo);

        // verificação
        Mockito.verify(lancamentoRepository, Mockito.times(1)).save(lancamentoSalvo);
    }

    @Test
    public void deveLancarErroAoTentarAtualizarUmLacamentoQueAindaNaoFoiSalvo(){
        // cenário
        Lancamento lancamentoASalvar = LancamentoRepositoryTest.getLancamento();
        Mockito.doThrow(RegraDeNegocioException.class).when(service).validar(lancamentoASalvar);

        // execução e verificação
        Assertions.catchThrowableOfType(()-> service.atualizar(lancamentoASalvar), NullPointerException.class);

        Mockito.verify(lancamentoRepository, Mockito.never()).save(lancamentoASalvar);
    }

    @Test
    public void deveDeletearUmLancamento(){
        // cenário
        Lancamento lancamentoASalvar = LancamentoRepositoryTest.getLancamento();
        lancamentoASalvar.setId(1L);

        // execução
        service.deletar(lancamentoASalvar);

        // verificação
        Mockito.verify(lancamentoRepository).delete(lancamentoASalvar);
    }

    @Test
    public void deveLancarErroAoTentarDeletarUmLancamentoQueAindaNaoFoiSalvo(){
        // cenário
        Lancamento lancamentoASalvar = LancamentoRepositoryTest.getLancamento();

        // execução
        Assertions.catchThrowableOfType(()-> service.deletar(lancamentoASalvar), NullPointerException.class);


        Mockito.verify(lancamentoRepository, Mockito.never()).delete(lancamentoASalvar);
    }

    @Test
    public void deveFiltrarLancamentos(){
        // cenario
        Lancamento lancamento = LancamentoRepositoryTest.getLancamento();
        lancamento.setId(1L);

        List<Lancamento> lista = Arrays.asList(lancamento);
        Mockito.when(lancamentoRepository.findAll(Mockito.any(Example.class))).thenReturn(lista);

        // execucao
        List<Lancamento> resultado = service.buscar(lancamento);

        // verificacoes
        Assertions.assertThat(resultado).isNotEmpty().hasSize(1).contains(lancamento);

    }

    @Test
    public void deveAtualizarOStatusDeUmLancamento(){
        // cenário
        Lancamento lancamento = LancamentoRepositoryTest.getLancamento();
        lancamento.setId(1l);
        lancamento.setStatus(StatusLancamento.PENDENTE);

        StatusLancamento novoStatus = StatusLancamento.EFETIVADO;
        Mockito.doReturn(lancamento).when(service).atualizar(lancamento);

        // execução
        service.atualizarStatus(lancamento, novoStatus);

        // verificações
        Assertions.assertThat(lancamento.getStatus()).isEqualTo(novoStatus);
        Mockito.verify(service).atualizar(lancamento);
    }

    @Test
    public void deveObterUmLancamentoPorID(){
        // cenário
        Long id= 1l;

        Lancamento lancamento = LancamentoRepositoryTest.getLancamento();
        lancamento.setId(id);

        Mockito.when(lancamentoRepository.findById(id)).thenReturn(Optional.of(lancamento));

        // execução
        Optional<Lancamento> resultado = service.obterPorId(id);

        // verificação
        Assertions.assertThat(resultado.isPresent()).isTrue();
    }

    @Test
    public void deveRetrornarVazioQuandoOLancamentoNaoExiste(){
        // cenário
        Long id= 1l;

        Lancamento lancamento = LancamentoRepositoryTest.getLancamento();
        lancamento.setId(id);

        Mockito.when(lancamentoRepository.findById(id)).thenReturn(Optional.empty());

        // execução
        Optional<Lancamento> resultado = service.obterPorId(id);

        // verificação
        Assertions.assertThat(resultado.isPresent()).isFalse();
    }
}
