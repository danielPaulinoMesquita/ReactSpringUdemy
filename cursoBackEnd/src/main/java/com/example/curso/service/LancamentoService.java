package com.example.curso.service;

import com.example.curso.model.entity.Lancamento;
import com.example.curso.model.entity.StatusLancamento;

import java.util.List;
import java.util.Optional;

public interface LancamentoService {

    Lancamento salvar(Lancamento lancamento);

    Lancamento atualizar(Lancamento lancamento);

    void deletar(Lancamento lancamento);

    List<Lancamento> buscar(Lancamento lancamentoFiltro);

    void atualizarStatus(Lancamento lancamento, StatusLancamento statusLancamento);

    void validar(Lancamento lancamento);

    Optional<Lancamento> obterPorId(Long id);
}
