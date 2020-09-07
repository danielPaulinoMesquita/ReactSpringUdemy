package com.example.curso.api.resources;

import com.example.curso.api.dto.UsuarioDTO;
import com.example.curso.exception.RegraDeNegocioException;
import com.example.curso.model.entity.Usuario;
import com.example.curso.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioResource {

    private UsuarioService usuarioService;

    public UsuarioResource(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity salvar(@RequestBody UsuarioDTO usuarioDTO){
        Usuario usuario = getUsuario("email@email.com", "senha");

        try {
            Usuario usuarioSalvo=usuarioService.salvarUsuario(usuario);
            return new ResponseEntity(usuarioSalvo, HttpStatus.CREATED);

        }catch (RegraDeNegocioException regraDeNegocioException){
            return ResponseEntity.badRequest().body(regraDeNegocioException.getMessage());      }
    }

    private Usuario getUsuario(String email, String senha) {
        Usuario usuario= new Usuario();
        usuario.setNome("pessoaPadrao");
        usuario.setEmail(email);
        usuario.setSenha(senha);
        return usuario;
    }
}
