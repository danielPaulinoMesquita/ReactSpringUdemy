package com.example.curso.api.resources;

import com.example.curso.api.dto.UsuarioDTO;
import com.example.curso.exception.ErroAutenticacao;
import com.example.curso.exception.RegraDeNegocioException;
import com.example.curso.model.entity.Usuario;
import com.example.curso.service.LancamentoService;
import com.example.curso.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioResource {

    private UsuarioService usuarioService;
    private LancamentoService lancamentoService;

    public UsuarioResource(UsuarioService usuarioService, LancamentoService lancamentoService) {
        this.usuarioService = usuarioService;
        this.lancamentoService = lancamentoService;
    }

    @PostMapping("/autenticar")
    public ResponseEntity autenticar(@RequestBody UsuarioDTO usuarioDTO){
        try {
            Usuario usuarioAutenticado= usuarioService.autenticar(usuarioDTO.getEmail(), usuarioDTO.getSenha());
            return ResponseEntity.ok(usuarioAutenticado);
        }catch (ErroAutenticacao e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity salvar(@RequestBody UsuarioDTO usuarioDTO){
        Usuario usuario = Usuario.mapperUsuarioDTOtoUsuario(usuarioDTO);

        try {
            Usuario usuarioSalvo = usuarioService.salvarUsuario(usuario);
            return new ResponseEntity(usuarioSalvo, HttpStatus.CREATED);

        }catch (RegraDeNegocioException regraDeNegocioException){
            return ResponseEntity.badRequest().body(regraDeNegocioException.getMessage());      }
    }

    @GetMapping("{id}/saldo")
    public ResponseEntity obterSaldo(@PathVariable("id") Long id){
        Optional<Usuario> usuario = usuarioService.obterPorId(id);

        if (!usuario.isPresent()){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        BigDecimal saldo = lancamentoService.obterSaldoPorUsuario(id);
        return ResponseEntity.ok(saldo);
    }

    private Usuario getUsuario(String email, String senha) {
        Usuario usuario= new Usuario();
        usuario.setNome("pessoaPadrao");
        usuario.setEmail(email);
        usuario.setSenha(senha);
        return usuario;
    }
}
