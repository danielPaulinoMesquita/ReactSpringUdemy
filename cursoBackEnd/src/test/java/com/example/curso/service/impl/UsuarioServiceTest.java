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
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Optional;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

    @MockBean
    UsuarioRepository repository;

    @SpyBean
    UsuarioServiceImpl service;

    public void deveSalvarUmUsuario(){
        //cenário
        Mockito.doNothing().when(service).validarEmail(Mockito.anyString());
        Usuario usuario= getUsuario("email@email.com","senha");
        Mockito.when(repository.save(Mockito.any(Usuario.class))).thenReturn(usuario);

        //acao
        Usuario usuarioSalvo= service.salvarUsuario(new Usuario());

        //Verificacao
        Assertions.assertThat(usuarioSalvo).isNotNull();
        Assertions.assertThat(usuarioSalvo.getId()).isEqualTo(1L);
        Assertions.assertThat(usuarioSalvo.getEmail()).isEqualTo("email@email.com");
        Assertions.assertThat(usuarioSalvo.getSenha()).isEqualTo("senha");

    }

    @Test(expected = RegraDeNegocioException.class)
    public void naoDeveSalvarUmUsuarioComEmailJaCadastrado(){
        //cenario
        Usuario usuario = getUsuario("email@email.com", "senha");
        Mockito.doThrow(RegraDeNegocioException.class).when(service).validarEmail("email@email.com");

        //acao
        service.salvarUsuario(usuario);

        //verificacao
        Mockito.verify(repository, Mockito.never()).save(usuario);

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

    @Test
    public void deveLancarErrorQuandoNaoEncontrarUsuarioCadastrado(){

        // Cenário
        Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());

        // Ação
        Throwable exception = Assertions.catchThrowable(() -> service.autenticar("email@email.com", "senha"));

        // Verificação
        Assertions.assertThat(exception).isInstanceOf(ErroAutenticacao.class).hasMessage("Usuário não encontrado para o email informado. ");

    }

    @Test
    public void deveLancarErroSenhaNaoBater(){

        // Cenário
        Usuario usuario = getUsuario("email@email.com","senha");
        Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(usuario));

        // Ação
        Throwable excepetion= Assertions.catchThrowable(() -> service.autenticar("email@email.com","1233"));
        Assertions.assertThat(excepetion).isInstanceOf(ErroAutenticacao.class).hasMessage("Senha Inválida.");

    }

    private Usuario getUsuario(String email, String senha) {
        Usuario usuario= new Usuario();
        usuario.setId(1L);
        usuario.setEmail(email);
        usuario.setSenha(senha);
        return usuario;
    }
}
