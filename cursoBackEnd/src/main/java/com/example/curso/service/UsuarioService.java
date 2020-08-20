package com.example.curso.service;

import com.example.curso.model.entity.Usuario;

public interface UsuarioService {

    Usuario autenticar (String email, String senha);

    Usuario salvarUsuario(Usuario usuario);

    void validarEmail(String email);
}
