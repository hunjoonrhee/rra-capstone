import React, {ChangeEvent, FormEvent, useEffect, useState} from 'react';
import logo from './logo.svg';
import './App.css';
import axios from "axios";
import {HashRouter, Route, Routes} from "react-router-dom";
import MainPage from "./pages/MainPage";
import RoutesPage from "./pages/RoutesPage";
import {LocationRequest} from "./model/LocationRequest";

function App() {


    const [locationRequest, setLocationRequest] = useState("");

    function setRequest(locationRequest:string){
        setLocationRequest(locationRequest);
        console.log(locationRequest)
    }

    const [routes, setRoutes] = useState([]);

    function getRoutesNearByLocationRequest(locationRequest:string){
        if(locationRequest.length>0){
            console.log("ddd", locationRequest)
            axios.get(`/api/route/routes?address=${locationRequest}`)
                .then((response)=> response.data)
                .then((routes) => setRoutes(routes))
                .catch((err)=>console.log((err)))
        }
    }

    useEffect(()=>{
        getRoutesNearByLocationRequest(locationRequest)
    }, [])


    return (
    <div className="App">
        <div>
        </div>
        <HashRouter>
            <Routes>
                <Route path = {"/"} element = {<MainPage setRequest={setRequest}
                                                         getRoutesNearByLocationRequest={getRoutesNearByLocationRequest}/>}/>
                <Route path = {"/routes"} element = {<RoutesPage routes={routes}/>}/>
            </Routes>
        </HashRouter>

    </div>
  );
}

export default App;
