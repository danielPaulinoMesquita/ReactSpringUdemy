import React from 'react'

import Home from '../views/home'
import CadastroUsuario from "../views/cadastroUsuario";
import ConsultaLancamento from "../views/consultaLancamento";
import Login from "../views/login";

import { Route, Switch, HashRouter } from 'react-router-dom'

function Rotas() {
        return (
            <HashRouter>
                <Switch>
                    <Route path="/home" component={Home}/>
                    <Route path="/cadastro-usuarios" component={CadastroUsuario}/>
                    <Route path="/consulta-lancamentos" component={ConsultaLancamento}/>
                    <Route path="/login" component={Login}/>
                </Switch>
            </HashRouter>
        )
}

export default Rotas;
