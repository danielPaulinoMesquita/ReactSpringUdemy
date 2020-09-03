package com.example.curso.model.repository;

import com.example.curso.model.entity.Usuario;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UsuarioRepositoryTest {

    @Autowired
    UsuarioRepository repository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    public void deveVerificarAExistenciaDeUmEmail(){
            // Cenário
        Usuario usuario= new Usuario();
        usuario.setNome("UsuarioTeste");
        usuario.setEmail("teste@email.com");
        usuario.setSenha("123");

        entityManager.persist(usuario);

            //ação /Execução
        boolean result = repository.existsByEmail("teste@email.com");

            //Verificação
        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void deveRetornarFalsoQuandoNãoHouverUsuario(){

        boolean result= repository.existsByEmail("teste@email.com");

        Assertions.assertThat(result).isFalse();
    }
}
