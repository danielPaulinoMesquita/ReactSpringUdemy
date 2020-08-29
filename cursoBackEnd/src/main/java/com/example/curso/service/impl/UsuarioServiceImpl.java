package com.example.curso.service.impl;

import com.example.curso.exception.ErroAutenticacao;
import com.example.curso.exception.RegraDeNegocioException;
import com.example.curso.model.entity.Usuario;
import com.example.curso.model.repository.UsuarioRepository;
import com.example.curso.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Usuario autenticar(String email, String senha) {
        Optional<Usuario> usuario= usuarioRepository.findByEmail(email);
        if(!usuario.isPresent()){
            throw new ErroAutenticacao("Usuário não encontrado para o email informado. ");
        }

        if (!usuario.get().getSenha().equals(senha)){
            throw  new ErroAutenticacao("Senha Inválida.");
        }

        return usuario.get();
    }

    @Override
    @Transient
    public Usuario salvarUsuario(Usuario usuario) {
        validarEmail(usuario.getEmail());
        return usuarioRepository.save(usuario);
    }

    @Override
    public void validarEmail(String email) {
        boolean existe= usuarioRepository.existsByEmail(email);
        if (existe){
            throw new RegraDeNegocioException("Já Existe um Usuário cadastrado com esse Email!");
        }
    }
}
