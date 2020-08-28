package com.example.curso.model.repository;

import com.example.curso.model.entity.Usuario;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UsuarioRepositoryTeste {

    @Autowired
    UsuarioRepository repository;

    @Test
    public void deveVerificarAExistenciaDeUmEmail(){
            // Cenário
        Usuario usuario= new Usuario();
        usuario.setNome("UsuarioTeste");
        usuario.setEmail("teste@email.com");
        repository.save(usuario);

            //ação /Execução
        boolean result = repository.existsByEmail("teste@email.com");

            //Verificação
        Assertions.assertThat(result).isTrue();
    }
}
