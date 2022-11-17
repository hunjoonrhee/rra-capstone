import React from 'react';
import './App.css';
import {HashRouter} from "react-router-dom";
import {ToastContainer} from "react-toastify";

import Main from "./Main";

function App() {




    return (
    <div className="App">
        <ToastContainer/>
        <div>
        </div>
        <HashRouter>
            <Main/>
        </HashRouter>

    </div>
  );
}

export default App;
