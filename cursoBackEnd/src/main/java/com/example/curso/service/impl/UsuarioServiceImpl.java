package com.example.curso.service.impl;

import com.example.curso.exception.RegraDeNegocioException;
import com.example.curso.model.entity.Usuario;
import com.example.curso.model.repository.UsuarioRepository;
import com.example.curso.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Usuario autenticar(String email, String senha) {
        return null;
    }

    @Override
    public Usuario salvarUsuario(Usuario usuario) {
        return null;
    }

    @Override
    public void validarEmail(String email) {
        boolean existe= usuarioRepository.existsByEmail(email);
        if (existe){
            throw new RegraDeNegocioException("Já Existe um Usuário cadastrado com esse Email!");
        }
    }
}
