package com.example.curso.service.impl;

import com.example.curso.exception.RegraDeNegocioException;
import com.example.curso.model.entity.Usuario;
import com.example.curso.model.repository.UsuarioRepository;
import com.example.curso.service.UsuarioService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

    @Autowired
    UsuarioRepository repository;

    @Autowired
    UsuarioService service;

    @Before
    public void init() {
        Usuario usuario= new Usuario();
        usuario.setNome("UsuarioTeste");
        usuario.setEmail("email@email.com");
        usuario.setSenha("123");

        repository.save(usuario);
    }

    @Test(expected = Test.None.class)
    public void deveValidarEmail() {
        // cenario
        repository.deleteAll();

        //ação
        service.validarEmail("email@email.com");
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveLancarErroAoValidarEmailQuandoExistirEmailCadastrado() throws RegraDeNegocioException{

        //acao
        service.validarEmail("email@email.com");
    }
}
