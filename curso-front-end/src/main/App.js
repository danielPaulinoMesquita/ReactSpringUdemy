import React from 'react';
import Rotas from './rotas'
import Navbar from '../components/navbar'

import 'bootswatch/dist/flatly/bootstrap.css'
import '../custom.css'

import 'toastr/build/toastr.min'
import 'toastr/build/toastr.css'

class App extends React.Component{
    render() {
        return(
            <>
            <Navbar></Navbar>
                <div className="container">
                    <Rotas/>
                </div>
            </>
        )
    }
}

export default App;
