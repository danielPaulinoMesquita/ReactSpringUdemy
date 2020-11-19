import React from 'react'

import Home from '../views/home'
import CadastroUsuario from "../views/cadastroUsuario";
import ConsultaLancamento from "../views/lancamentos/consultaLancamento";
import CadastroLancamentos from "../views/lancamentos/cadastroLancamento";
import Login from "../views/login";

import { Route, Switch, HashRouter, Redirect } from 'react-router-dom'

const isUsuarioAutenticado = () => {
    return false;
}

function RotaAutenticada({ component: Component, ...props }) {
    return(
        <Route {...props} render={(componentProps) => {
            if(isUsuarioAutenticado()){
                return (
                    <Component {...componentProps}/>
                )
            }else {
                return (
                    <Redirect to={{pathname: '/login', state: {from: componentProps.location}}}/>
                )
            }
        }} />
    )
}
function Rotas() {
        return (
            <HashRouter>
                <Switch>
                    <Route path="/login" component={Login}/>
                    <Route path="/cadastro-usuarios" component={CadastroUsuario}/>
                    <RotaAutenticada path="/home" component={Home}/>
                    <RotaAutenticada path="/consulta-lancamentos" component={ConsultaLancamento}/>
                    <RotaAutenticada path="/cadastro-lancamentos/:id?" component={CadastroLancamentos}/>
                </Switch>
            </HashRouter>
        )
}

export default Rotas;
