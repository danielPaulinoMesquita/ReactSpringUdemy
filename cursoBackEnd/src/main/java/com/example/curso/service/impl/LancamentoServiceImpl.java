package com.example.curso.service.impl;

import com.example.curso.exception.RegraDeNegocioException;
import com.example.curso.model.entity.Lancamento;
import com.example.curso.model.entity.StatusLancamento;
import com.example.curso.model.entity.enums.TipoLancamento;
import com.example.curso.model.repository.LancamentoRepository;
import com.example.curso.service.LancamentoService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class LancamentoServiceImpl implements LancamentoService {

    private LancamentoRepository lancamentoRepository;

    public LancamentoServiceImpl(LancamentoRepository lancamentoRepository) {
        this.lancamentoRepository = lancamentoRepository;
    }

    @Override
    @Transactional
    public Lancamento salvar(Lancamento lancamento) {
        validar(lancamento);
        lancamento.setStatus(StatusLancamento.PENDENTE);
        return lancamentoRepository.save(lancamento);
    }

    @Override
    @Transactional
    public Lancamento atualizar(Lancamento lancamento) {
        Objects.requireNonNull(lancamento.getId());
        validar(lancamento);
        return lancamentoRepository.save(lancamento);
    }

    @Override
    @Transactional(readOnly = true)
    public void deletar(Lancamento lancamento) {
        lancamentoRepository.delete(lancamento);
    }

    /**
     * Example é uma classe da API do spring que filtra de acordo
     * com o objeto passsado pra ele
     * @param lancamentoFiltro
     * @return
     */
    @Override
    @org.springframework.transaction.annotation.Transactional( readOnly = true)
    public List<Lancamento> buscar(Lancamento lancamentoFiltro) {
        Example example= Example.of(lancamentoFiltro,
                ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));

        return lancamentoRepository.findAll(example);
    }

    @Override
    public void atualizarStatus(Lancamento lancamento, StatusLancamento statusLancamento) {
        lancamento.setStatus(statusLancamento);
        atualizar(lancamento);
    }

    @Override
    public void validar(Lancamento lancamento) {

        if(lancamento.getDescricao() == null || lancamento.getDescricao().trim().equals("")){
            throw  new RegraDeNegocioException("Informe uma Descrição Válida.");
        }

        if(lancamento.getMes() == 0 || lancamento.getMes() < 1 || lancamento.getMes() > 12){
            throw  new RegraDeNegocioException("Informe um Mês válido.");
        }

        if(String.valueOf(lancamento.getAno()).length() !=4 ){
            throw  new RegraDeNegocioException("Informe um Ano Válido.");
        }

        if(lancamento.getUsuario() == null || lancamento.getUsuario().getId() == null){
            throw  new RegraDeNegocioException("Informe um Usuário.");
        }

        if(lancamento.getValor() == null || lancamento.getValor().compareTo(BigDecimal.ZERO) < 1){
            throw  new RegraDeNegocioException("Informe um Valor Válido.");
        }

        if(lancamento.getTipo() == null){
            throw new RegraDeNegocioException("Informe um tipo de lançamento");
        }
    }

    @Override
    public Optional<Lancamento> obterPorId(Long id) {
        return lancamentoRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal obterSaldoPorUsuario(Long id) {
        BigDecimal receitas = lancamentoRepository.obterSaldoPorTipoLancamentoEUsuario(id, TipoLancamento.RECEITA);
        BigDecimal despesas = lancamentoRepository.obterSaldoPorTipoLancamentoEUsuario(id, TipoLancamento.DESPESA);

        if(receitas == null){
            receitas = BigDecimal.ZERO;
        }

        if(despesas == null){
            despesas = BigDecimal.ZERO;
        }

        return receitas.subtract(despesas);
    }

}
