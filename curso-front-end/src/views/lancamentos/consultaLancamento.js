import React from 'react';
import {withRouter} from 'react-router-dom';
import Card from '../../components/card';
import FormGroup from '../../components/form-group';
import SelectMenu from '../../components/selectMenu';
import TableLancamento from './tableLancamento';
import LancamentoService from '../../app/service/lancamentoService';
import LocalStorageService from "../../app/service/localStorageService";
import * as messages from '../../components/toastr'

class ConsultaLancamento extends React.Component{

    state = {
        ano:'',
        mes: '',
        tipo: '',
        descricao: '',
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
            this.setState({lancamentos: response.data})
        }).catch(error => {
            console.log(error)
        });
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
                        <button  onClick={this.buscar} className="btn btn-success">Buscar</button>
                        <button  className="btn btn-danger">Cadastrar</button>
                    </div>
                </div>
                <br/>
                <div className="row">
                    <div className="col-md-12">
                        <div className="bs-component">
                            <TableLancamento lancamentos={this.state.lancamentos}/>
                        </div>
                    </div>
                </div>
            </Card>

        )
    }
}

export default withRouter(ConsultaLancamento);
