package com.example.curso.model.entity;

import com.example.curso.api.dto.UsuarioDTO;
import com.example.curso.exception.RegraDeNegocioException;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "email")
    private String email;

    @Column(name = "senha")
    @JsonIgnore
    private String senha;

    public Usuario() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id) &&
                Objects.equals(email, usuario.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                '}';
    }

    public static Usuario mapperUsuarioDTOtoUsuario(UsuarioDTO usuarioDTO){
        if (!(usuarioDTO.getEmail()!=null && usuarioDTO.getNome()!=null && usuarioDTO.getSenha() != null)){
            throw  new RegraDeNegocioException("Campos nulos ao converter UsuarioDTO para Usuario ");
        }
       Usuario usuario = new Usuario();
       usuario.setNome(usuarioDTO.getNome());
       usuario.setEmail(usuarioDTO.getEmail());
       usuario.setSenha(usuarioDTO.getSenha());
       return usuario;
    }
}
