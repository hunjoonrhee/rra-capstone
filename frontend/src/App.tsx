import React from 'react';
import './App.css';
import {HashRouter, Route, Routes} from "react-router-dom";
import MainPage from "./pages/MainPage";
import RoutesPage from "./pages/RoutesPage";
import useMyRoutes from "./hooks/useMyRoutes";

function App() {

    const {setRequest, saveFoundRoutes} = useMyRoutes()

    return (
    <div className="App">
        <div>
        </div>
        <HashRouter>
            <Routes>
                <Route path = {"/"} element = {<MainPage saveFoundRoutes={saveFoundRoutes} setRequest={setRequest}/>}/>
                <Route path = {"/routes/:id"} element = {<RoutesPage saveFoundRoutes={saveFoundRoutes} setRequest={setRequest}/>}/>
            </Routes>
        </HashRouter>

    </div>
  );
}

export default App;
