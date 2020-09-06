package com.example.curso.service.impl;

import com.example.curso.exception.ErroAutenticacao;
import com.example.curso.exception.RegraDeNegocioException;
import com.example.curso.model.entity.Usuario;
import com.example.curso.model.repository.UsuarioRepository;
import com.example.curso.service.UsuarioService;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Optional;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

    @MockBean
    UsuarioRepository repository;

    @MockBean
    UsuarioService service;

    @Before
    public void init() {
        service = new UsuarioServiceImpl(repository);
    }

    @Test(expected = Test.None.class)
    public void deveValidarEmail() {
        // cenario
        Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false);

        //ação
        service.validarEmail("email@email.com");
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveLancarErroAoValidarEmailQuandoExistirEmailCadastrado() throws RegraDeNegocioException{
        //Cenário
       Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(true);

        //acao
        service.validarEmail(Mockito.anyString());
    }

    @Test(expected = Test.None.class)
    public void deveAutenticarUmUsuarioComSucesso(){
        // Cenário
        String email= "email@email.com";
        String senha= "senha";

        Usuario usuario = getUsuario(email, senha);

        Mockito.when(repository.findByEmail(email)).thenReturn(Optional.of(usuario));

        // Ação
        Usuario result = service.autenticar(email,senha);

        // Verificação
        Assertions.assertThat(result).isNotNull();
    }

    @Test(expected = ErroAutenticacao.class)
    public void deveLancarErrorQuandoNaoEncontrarUsuarioCadastrado(){

        // Cenário
        Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());

        // Ação
        service.autenticar("email@email.com","senha");
    }

    @Test(expected = ErroAutenticacao.class)
    public void deveLancarErroSenhaNãoBater(){

        // Cenário
        Usuario usuario = getUsuario("email@email.com","senha");
        Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(usuario));

        // Ação
        service.autenticar("email@email.com","1233");
    }

    private Usuario getUsuario(String email, String senha) {
        Usuario usuario= new Usuario();
        usuario.setId(1L);
        usuario.setEmail(email);
        usuario.setSenha(senha);
        return usuario;
    }
}
