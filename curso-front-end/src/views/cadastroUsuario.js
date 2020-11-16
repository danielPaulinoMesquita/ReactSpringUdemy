import React from 'react'

import Card from "../components/card";
import FormGroup from "../components/form-group";
import {withRouter} from 'react-router-dom'
import {mensagemSucesso, mensagemErro} from "../components/toastr";

import UsuarioService from "../app/service/usuarioService";

class CadastroUsuario extends React.Component{

    state = {
        nome: '',
        email: '',
        senha: '',
        senhaRepeticao: ''
    }

    constructor() {
        super();
        this.service = new UsuarioService();
    }

    validar(){
        const msgs = [];

        if (!this.state.nome){
            msgs.push('O campo Nome é obrigatório. ')
        }

        if (!this.state.email){
            msgs.push('O campo Email é obrigatório. ')
        }else if (!this.state.email.match(/^[a-z0-9.]+@[a-z0-9]+\.[a-z]/)){
            msgs.push('Informe um Email válido. ')
        }

        if (!this.state.senha || !this.state.senhaRepeticao){
            msgs.push('Digite a senha 2x. ')
        }else if(this.state.senha !== this.state.senhaRepeticao){
            msgs.push('As senhas não convergem')
        }

        return msgs;
    }

    cadastrar = () =>{
        const msgs = this.validar();

        if(msgs && msgs.length > 0){
            msgs.forEach((msg) => {
                mensagemErro(msg)
            })

            return false
        }

        const usuario = {
            nome: this.state.nome,
            email: this.state.email,
            senha: this.state.senha
        }

        this.service.salvar(usuario)
            .then(response=> {
                mensagemSucesso('Usuário cadastrado com sucesso! ' +
                    'Faça o login para acessar o sistema')
                this.props.history.push('/login')
            }).catch(error =>{
                mensagemErro(error.response.data)
        });
    }

    voltar = () => {
        this.props.history.push('/login')
    }
    render() {
        return(
            <Card title="Cadastro de Usuário">
                <div className="row">
                    <div className="col-lg-12">
                        <div className="bs-component">
                            <FormGroup label="Nome: *" htmlFor="inputNome">
                                <input className="form-control"
                                       type="text"
                                       id="inputNome"
                                       name="nome"
                                       onChange={e => this.setState({nome: e.target.value})}/>
                            </FormGroup>
                            <FormGroup label="Email: *" htmlFor="inputEmail">
                                <input className="form-control"
                                       type="email"
                                       id="inputEmail"
                                       name="email"
                                       onChange={e => this.setState({email: e.target.value})}/>
                            </FormGroup>
                            <FormGroup label="Senha: *" htmlFor="inputSenha">
                                <input className="form-control"
                                       type="password"
                                       id="inputSenha"
                                       name="senha"
                                       onChange={e => this.setState({senha: e.target.value})}/>
                            </FormGroup>
                            <FormGroup label="Repita a Senha: *" htmlFor="inputRepitaSenha">
                                <input className="form-control"
                                       type="password"
                                       id="inputRepitaSenha"
                                       name="senhaRepeticao"
                                       onChange={e => this.setState({senhaRepeticao: e.target.value})}/>
                            </FormGroup>
                            <button onClick={this.cadastrar} type="button" className="btn btn-success">Salvar</button>
                            <button onClick={this.voltar} type="button" className="btn btn-danger">Voltar</button>
                        </div>
                    </div>
                </div>
            </Card>
        )
    }
}

export default withRouter(CadastroUsuario);
