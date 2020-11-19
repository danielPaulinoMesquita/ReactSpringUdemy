import React from 'react'

import Navbaritem from "./navbaritem";
import AuthService from "../app/service/authService";

const deslogar = () => {
    AuthService.removerUsuarioAutenticado();
}

const isUsuarioAutenticado = () => {
    return AuthService.isUsuarioAutenticado();
}

function Navbar(){
    return (
        <div className="navbar navbar-expand-lg fixed-top navbar-dark bg-primary">
            <div className="container">
                <a href="#/home" className="navbar-brand">Minhas
                    Finanças</a>
                <button className="navbar-toggler" type="button" data-toggle="collapse"
                        data-target="#navbarResponsive"
                        aria-controls="navbarResponsive"
                        aria-expanded="false"
                        aria-label="Toggle navigation">
                    <span className="navbar-toggler-icon"></span>
                </button>
                <div className="collapse navbar-collapse" id="navbarResponsive">
                    <ul className="navbar-nav">
                        <Navbaritem render={isUsuarioAutenticado()} href="#/home" label="Home"/>
                        <Navbaritem render={isUsuarioAutenticado()}href="#/cadastro-usuarios" label="Usuários" />
                        <Navbaritem render={isUsuarioAutenticado()} href="#/consulta-lancamentos" label="Lançamentos" />
                        <Navbaritem render={isUsuarioAutenticado()}onClick={deslogar} href="#/login" label="Sair" />
                    </ul>
                </div>
            </div>
        </div>
    )
}

export default Navbar;


