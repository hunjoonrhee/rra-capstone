import React from 'react';
import './App.css';
import {HashRouter, Route, Routes} from "react-router-dom";
import MainPage from "./pages/MainPage";
import RoutesPage from "./pages/RoutesPage";
import useMyRoutes from "./hooks/useMyRoutes";
import SignInPage from "./pages/SignInPage";
import SignUpPage from "./pages/SignUpPage";
import {ToastContainer} from "react-toastify";
import RouteDetailsPage from "./pages/RouteDetailsPage";

function App() {

    const {setRequest, saveFoundRoutes} = useMyRoutes()



    return (
    <div className="App">
        <ToastContainer/>
        <div>
        </div>
        <HashRouter>
            <Routes>
                <Route path = {"/"} element = {<MainPage saveFoundRoutes={saveFoundRoutes} setRequest={setRequest}/>}/>
                <Route path = {"/sign-in"} element = {<SignInPage/>}/>
                <Route path = {"/sign-up"} element = {<SignUpPage/>}/>
                <Route path = {"/routes/:id"} element = {<RoutesPage saveFoundRoutes={saveFoundRoutes} setRequest={setRequest}/>}/>
                <Route path = {"/route/:id/details"} element = {<RouteDetailsPage/>}/>
            </Routes>
        </HashRouter>

    </div>
  );
}

export default App;
