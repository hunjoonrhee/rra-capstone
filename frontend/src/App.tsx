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
import useSecurity from "./hooks/useSecurity";

function App() {

    const {setRequest, saveFoundRoutes, routes, handleLocationChange, location} = useMyRoutes()
    const {me, handleLogin, setUserName, setUserPassword, register, handleLogout} = useSecurity()



    return (
    <div className="App">
        <ToastContainer/>
        <div>
        </div>
        <HashRouter>
            <Routes>
                <Route path = {"/"} element = {<MainPage me={me} saveFoundRoutes={saveFoundRoutes}
                                                         setRequest={setRequest} handleLogout={handleLogout}
                                                         location={location} handleLocationChange={handleLocationChange}/>}/>
                <Route path = {"/sign-in"} element = {<SignInPage me={me} handleLogin={handleLogin} handleLogout={handleLogout}
                                                      setUserName={setUserName} setUserPassword={setUserPassword}/>}/>
                <Route path = {"/sign-up"} element = {<SignUpPage me={me} register={register} handleLogout={handleLogout}/>}/>
                <Route path = {"/routes/:id"} element = {<RoutesPage me={me} saveFoundRoutes={saveFoundRoutes}
                                                                     location={location} setRequest={setRequest} handleLogout={handleLogout}/>}/>
                <Route path = {"/route/:id/details"} element = {<RouteDetailsPage me={me} routes={routes} handleLogout={handleLogout} location={location}/>}/>
            </Routes>
        </HashRouter>

    </div>
  );
}

export default App;
