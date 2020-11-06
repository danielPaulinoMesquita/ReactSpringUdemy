package com.example.curso.api.resources;

import com.example.curso.api.dto.AtualizaStatusDTO;
import com.example.curso.api.dto.LancamentoDTO;
import com.example.curso.exception.RegraDeNegocioException;
import com.example.curso.model.entity.Lancamento;
import com.example.curso.model.entity.StatusLancamento;
import com.example.curso.model.entity.Usuario;
import com.example.curso.model.entity.enums.TipoLancamento;
import com.example.curso.service.LancamentoService;
import com.example.curso.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/lancamentos")
public class LancamentoResource {

    private LancamentoService lancamentoService;
    private UsuarioService usuarioService;

    public LancamentoResource(LancamentoService lancamentoService, UsuarioService usuarioService){
        this.usuarioService = usuarioService;
        this.lancamentoService = lancamentoService;
    }

    /**
     * Método de busca com filtro. Poderia ser
     * usado um Map, Ex: @RequestParam Map<String, String> params</String,>
     * @param descricao
     * @param mes
     * @param ano
     * @param idUsuario
     * @return
     */
    @GetMapping
    public ResponseEntity buscar(@RequestParam(value = "descricao", required = false) String descricao,
                                 @RequestParam(value = "mes", required = false) Integer mes,
                                 @RequestParam(value = "ano", required = false) Integer ano,
                                 @RequestParam(value = "usuario") Long idUsuario){
        Lancamento lancamentoFiltro = new Lancamento();
        lancamentoFiltro.setDescricao(descricao);
        if (mes != null) lancamentoFiltro.setMes(mes);
        if (ano != null) lancamentoFiltro.setAno(ano);

        Optional<Usuario> usuario = usuarioService.obterPorId(idUsuario);
        if (!usuario.isPresent()){
            return ResponseEntity.badRequest().body("Não foi possível realizar consulta. Usuário não encontrado para o id informado");
        }else {
            lancamentoFiltro.setUsuario(usuario.get());
        }

        List<Lancamento> lancamentoList = lancamentoService.buscar(lancamentoFiltro);
        return ResponseEntity.ok(lancamentoList);
    }

    @PostMapping
    public ResponseEntity salvar(@RequestBody LancamentoDTO lancamentoDTO){
        try {
            Lancamento lancamento = converter(lancamentoDTO);
            lancamento = lancamentoService.salvar(lancamento);
            return new ResponseEntity(lancamento, HttpStatus.CREATED);
        }catch (RegraDeNegocioException re){
            return  ResponseEntity.badRequest().body(re.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable Long id, @RequestBody LancamentoDTO lancamentoDTO){
        return lancamentoService.obterPorId(id).map(lancamento -> {
            try {
                Lancamento lancamento1 = converter(lancamentoDTO);
                lancamento1.setId(lancamento.getId());
                lancamentoService.atualizar(lancamento1);
                return ResponseEntity.ok(lancamento1);
            }catch (RegraDeNegocioException re){
                return ResponseEntity.badRequest().body(re.getMessage());
            }
        }).orElseGet(
                ()-> new ResponseEntity("Lancamento não encontrado na base de Dados.", HttpStatus.BAD_REQUEST));
    }

    @PutMapping("{id}/atualiza-status")
    public ResponseEntity atualizarStatus(@PathVariable("id") Long id, @RequestBody AtualizaStatusDTO atualizaStatusDTO){
        return lancamentoService.obterPorId(id).map(lancamentoEncontrado -> {
            StatusLancamento statusSelecionado = StatusLancamento.valueOf(atualizaStatusDTO.getStatus());

            if(statusSelecionado == null){
                return ResponseEntity.badRequest().body("Não foi possível atualizar o status do lançamento, envie um status válido! ");
            }

            try {
                lancamentoEncontrado.setStatus(statusSelecionado);
                lancamentoService.atualizar(lancamentoEncontrado);
                return ResponseEntity.ok(lancamentoEncontrado);
            }catch (RegraDeNegocioException re){
                return ResponseEntity.badRequest().body(re.getMessage());
            }
        }).orElseGet(
                ()-> new ResponseEntity("Lancamento não encontrado na base de Dados.", HttpStatus.BAD_REQUEST));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletar(@PathVariable("id") Long id){
        return lancamentoService.obterPorId(id).map(lancamentoEncontrado -> {
            lancamentoService.deletar(lancamentoEncontrado);
            return new ResponseEntity(HttpStatus.ACCEPTED);
        }).orElseGet(
                ()-> new ResponseEntity("Lancamento não encontrado na base de Dados.", HttpStatus.BAD_REQUEST));
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

        if (dto.getTipo() != null){
            lancamento.setTipo(TipoLancamento.valueOf(dto.getTipo()));
        }

        if (dto.getStatus() != null){
            lancamento.setStatus(StatusLancamento.valueOf(dto.getStatus()));
        }

        return lancamento;
    }
}
