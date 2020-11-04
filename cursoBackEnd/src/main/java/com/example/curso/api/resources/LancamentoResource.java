package com.example.curso.api.resources;

import com.example.curso.api.dto.LancamentoDTO;
import com.example.curso.exception.RegraDeNegocioException;
import com.example.curso.model.entity.Lancamento;
import com.example.curso.model.entity.StatusLancamento;
import com.example.curso.model.entity.Usuario;
import com.example.curso.model.entity.enums.TipoLancamento;
import com.example.curso.service.LancamentoService;
import com.example.curso.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lancamentos")
public class LancamentoResource {

    private LancamentoService lancamentoService;
    private UsuarioService usuarioService;

    public LancamentoResource(LancamentoService lancamentoService){
        this.lancamentoService = lancamentoService;
    }

    @PostMapping
    public ResponseEntity salvar(@RequestBody LancamentoDTO lancamentoDTO){

    }

    private Lancamento converter(LancamentoDTO dto){
        Lancamento lancamento = new Lancamento();
        lancamento.setId(dto.getId());
        lancamento.setDescricao(dto.getDescricao());
        lancamento.setAno(dto.getAno());
        lancamento.setMes(dto.getMes());
        lancamento.setValor(dto.getValor());

        lancamento.setUsuario(usuarioService.obterPorId(dto.getUsuario())
                .orElseThrow(() -> new RegraDeNegocioException("Usuário não encontrado para o Id informado")));

        lancamento.setTipo(TipoLancamento.valueOf(dto.getTipo()));
        lancamento.setStatus(StatusLancamento.valueOf(dto.getStatus()));
        return lancamento;

    }
}
