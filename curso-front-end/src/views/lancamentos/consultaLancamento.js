import React from 'react';
import {withRouter} from 'react-router-dom';
import Card from "../../components/card";
import FormGroup from "../../components/form-group";
import SelectMenu from "../../components/selectMenu";
import TableLancamento from './tableLancamento';
import LancamentoService from "../../app/service/lancamentoService";
import LocalStorageService from "../../app/service/localStorageService";

class ConsultaLancamento extends React.Component{

    state = {
        ano:'',
        mes: '',
        tipo: '',
        lancamentos : []
    }

    constructor() {
        super();
        this.service = new LancamentoService();
    }

    buscar = () => {
        const usuarioLogado = LocalStorageService.obterItem('_usuario_logado');

        const lancamentoFiltro = {
            ano: this.state.ano,
            mes: this.state.mes,
            tipo: this.state.tipo,
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
        const meses = [
            {label: 'Selecione ...', value: ''},
            {label: 'Janeiro', value: '1'},
            {label: 'Fevereiro', value: '2'},
            {label: 'Março', value: '3'},
            {label: 'Abril', value: '4'},
            {label: 'Maio', value: '5'},
            {label: 'Junho', value: '6'},
            {label: 'Julho', value: '7'},
            {label: 'Agosto', value: '8'},
            {label: 'Setembro', value: '9'},
            {label: 'Outubro', value: '10'},
            {label: 'Novembro', value: '11'},
            {label: 'Dezembro', value: '12'},
        ]

        const tipos = [
            {label: 'Selecione ...', value:''},
            {label: 'Despesa', value:'DESPESA'},
            {label: 'Receita', value:'RECEITA'},
        ]

        const lancamentos = [
            {id: 1, descricao: 'Divida do bruno', valor:300.00, tipo:'DESPESA', mes: 1, status: 'Efetivado'},
            {id: 2, descricao: 'Divida do bruno', valor:300.00, tipo:'DESPESA', mes: 1, status: 'Efetivado'}
        ]

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
