import React from 'react';
import {withRouter} from 'react-router-dom';
import Card from '../../components/card';
import FormGroup from '../../components/form-group';
import SelectMenu from '../../components/selectMenu';
import TableLancamento from './tableLancamento';
import LancamentoService from '../../app/service/lancamentoService';
import LocalStorageService from "../../app/service/localStorageService";
import * as messages from '../../components/toastr'

import { Dialog } from 'primereact/dialog';
import { Button } from 'primereact/button';

class ConsultaLancamento extends React.Component{

    state = {
        ano:'',
        mes: '',
        tipo: '',
        descricao: '',
        showConfirmDialog: false,
        lancamentoDeletar: {},
        lancamentos : []
    }

    constructor() {
        super();
        this.service = new LancamentoService();
    }

    buscar = () => {
        if(!this.state.ano){
            messages.mensagemErro('O preenchimento do campo Ano é obrigatório!')
            return false;
        }

        const usuarioLogado = LocalStorageService.obterItem('_usuario_logado');

        const lancamentoFiltro = {
            ano: this.state.ano,
            mes: this.state.mes,
            tipo: this.state.tipo,
            descricao: this.state.descricao,
            usuario: usuarioLogado.id
        }

        this.service.consultar(lancamentoFiltro)
            .then(response =>{
                const lista = response.data;

                if(lista.length < 1){
                    messages.mensagemAlerta("Nenhum resultado encontrado.")
                }

                this.setState({lancamentos: lista})
        }).catch(error => {
            console.log(error)
        });
    }

    editar = (id) => {
        this.props.history.push(`cadastro-lancamentos/${id}`);
    }

    abrirConfirmacao = (lancamento) => {
        console.log(lancamento);
        this.setState({showConfirmDialog: true, lancamentoDeletar: lancamento})
    }

    cancelarDelecao = () => {
        this.setState({showConfirmDialog: false, lancamentoDeletar: {}})
    }

    deletar = () => {
        this.service
            .deletar(this.state.lancamentoDeletar.id)
            .then(response => {
                const lancamentos = this.state.lancamentos;
                const index = lancamentos.indexOf(this.state.lancamentoDeletar);
                lancamentos.splice(index,1);
                this.setState({lancamentos: lancamentos, showConfirmDialog: false});

                messages.mensagemSucesso('Lançamento deletado com sucesso!')
            })
            .catch(error => {
                messages.mensagemErro('Ocorreu um erro ao tentar deletar o Lançamento')
            })
    }

    prepareCadastrar = () =>{
        this.props.history.push('/cadastro-lancamentos')
    }

    alterarStatus = (lancamento, status) => {
        this.service
            .alterarStatus(lancamento.id, status)
            .then( () =>{
                const lancamentos = this.state.lancamentos;
                const index = lancamentos.indexOf(lancamento);

                if(index !== -1){
                    lancamento['status'] = status;
                    lancamentos[index] = lancamento;
                    this.setState({lancamentos})
                }

                messages.mensagemSucesso('Status atualizado com sucesso!')
            })
    }

    renderFooter() {
        return (
            <div>
                <Button label="Não" onClick={this.cancelarDelecao} icon="pi pi-times" className="p-button-text">
                </Button>
                <Button label="Sim" onClick={this.deletar} icon="pi pi-check" autoFocu>
                </Button>
            </div>
        );
    }

    render() {
        const meses = this.service.obterListaMeses();
        const tipos = this.service.obterListaTipos();

        return (
            <Card title="Consulta Lançamentos">
                <div className="row">
                    <div className="col-md-6">
                        <div className="bs-component">
                            <FormGroup htmlFor="inputAno" label="Ano: *">
                                <input type="text"
                                       className="form-control"
                                       id="inputAno"
                                       value={this.state.ano}
                                       onChange={e => this.setState({ano: e.target.value})}
                                       placeholder="Digite o Ano"/>
                            </FormGroup>

                            <FormGroup htmlFor="inputDescricao" label="Descrição: *">
                                <input type="text"
                                       className="form-control"
                                       id="inputDescricao"
                                       value={this.state.descricao}
                                       onChange={e => this.setState({descricao: e.target.value})}
                                       placeholder="Digite a descrição"/>
                            </FormGroup>

                            <FormGroup htmlFor="inputMes" label="Mês: *">
                                <SelectMenu id="inputMes"
                                            value={this.state.mes}
                                            onChange={e=> this.setState({mes: e.target.value})}
                                            className="form-control"
                                            lista={meses}/>
                            </FormGroup>

                            <FormGroup htmlFor="inputTipo" label="Tipo Lançamento: ">
                                <SelectMenu id="inputTipo"
                                            value={this.state.tipo}
                                            onChange={e=> this.setState({tipo: e.target.value})}
                                            className="form-control"
                                            lista={tipos}/>
                            </FormGroup>

                        </div>
                        <button  onClick={this.buscar}
                                 className="btn btn-success">
                            <i className="pi pi-search"/> Buscar
                        </button>
                        <button  onClick={this.prepareCadastrar}
                                 className="btn btn-danger">
                            <i className="pi pi-plus"/> Cadastrar
                        </button>
                    </div>
                </div>
                <br/>
                <div className="row">
                    <div className="col-md-12">
                        <div className="bs-component">
                            <TableLancamento
                                lancamentos={this.state.lancamentos}
                                editAction={this.editar}
                                deleteAction={this.abrirConfirmacao}
                                alterarStatus={this.alterarStatus}/>
                        </div>
                    </div>
                </div>
                <div>
                    <Dialog header="Confirmação"
                            visible={this.state.showConfirmDialog}
                            style={{ width: '350px' }}
                            modal={true}
                            footer={this.renderFooter('displayConfirmation')}
                            onHide={() => this.setState({showConfirmDialog: false})}>
                        <div className="dialog-demo confirmation-content">
                            <span>Deseja excluir este Lançamento?</span>
                        </div>
                    </Dialog>
                </div>
            </Card>

        )
    }
}

export default withRouter(ConsultaLancamento);
