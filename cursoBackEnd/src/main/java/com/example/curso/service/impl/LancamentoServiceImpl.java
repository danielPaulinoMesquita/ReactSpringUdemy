package com.example.curso.service.impl;

import com.example.curso.model.entity.Lancamento;
import com.example.curso.model.entity.StatusLancamento;
import com.example.curso.model.repository.LancamentoRepository;
import com.example.curso.service.LancamentoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LancamentoServiceImpl implements LancamentoService {

    private LancamentoRepository lancamentoRepository;

    public LancamentoServiceImpl(LancamentoRepository lancamentoRepository) {
        this.lancamentoRepository = lancamentoRepository;
    }

    @Override
    public Lancamento salvar(Lancamento lancamento) {
        return null;
    }

    @Override
    public Lancamento atualizar(Lancamento lancamento) {
        return null;
    }

    @Override
    public void deletar(Lancamento lancamento) {

    }

    @Override
    public List<Lancamento> buscar(Lancamento lancamentoFiltro) {
        return null;
    }

    @Override
    public void atualizarStatus(Lancamento lancamento, StatusLancamento statusLancamento) {

    }
}
