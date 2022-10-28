import React from 'react';
import './App.css';
import {HashRouter, Route, Routes} from "react-router-dom";
import MainPage from "./pages/MainPage";
import RoutesPage from "./pages/RoutesPage";
import useMyRoutes from "./hooks/useMyRoutes";

function App() {

    const {setRequest, routes, getRoutesNearByLocationRequest} = useMyRoutes()

    return (
    <div className="App">
        <div>
        </div>
        <HashRouter>
            <Routes>
                <Route path = {"/"} element = {<MainPage setRequest={setRequest}
                                                         getRoutesNearByLocationRequest={getRoutesNearByLocationRequest}/>}/>
                <Route path = {"/routes"} element = {<RoutesPage routes={routes}
                                                                 setRequest={setRequest}
                                                                 getRoutesNearByLocationRequest={getRoutesNearByLocationRequest}/>}/>
            </Routes>
        </HashRouter>

    </div>
  );
}

export default App;
