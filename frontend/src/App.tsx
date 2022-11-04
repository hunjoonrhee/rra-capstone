import React, {useState} from 'react';
import './App.css';
import {HashRouter, Route, Routes} from "react-router-dom";
import MainPage from "./pages/MainPage";
import RoutesPage from "./pages/RoutesPage";
import useMyRoutes from "./hooks/useMyRoutes";
import SignInPage from "./pages/SignInPage";
import SignUpPage from "./pages/SignUpPage";
import {ToastContainer} from "react-toastify";
import RouteDetailsPage from "./pages/RouteDetailsPage";
import axios from "axios";
import {LocationReturn} from "./model/LocationReturn";

function App() {

    const {setRequest, saveFoundRoutes} = useMyRoutes()
    const [currentAddress, setCurrentAddress] = useState<LocationReturn>({
        address: {city:"",
            country_code:"",
            postcode:"",
            road:"",
            state:"",
            suburb:""},
        display_name: "",
        lat: "",
        lon: "",
        osm_id: undefined
    });



    function getCurrentLocation(lat:number, lon:number) {
        console.log(lat, lon)
        axios.get("https://nominatim.openstreetmap.org/reverse?lat="+lat+"&lon="+lon+"&format=json")
            .then((response)=> {return response.data })
            .then((data)=>{setCurrentAddress(data)})
            .finally(()=>console.log("places are: ", currentAddress))
            .catch((err)=>console.log("err: ", err))
    }


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
                <Route path = {"/routes/:id"} element = {<RoutesPage saveFoundRoutes={saveFoundRoutes} setRequest={setRequest}
                                                                     getCurrentLocation={getCurrentLocation} currentAddress={currentAddress}/>}/>
                <Route path = {"/route/:id/details"} element = {<RouteDetailsPage/>}/>
            </Routes>
        </HashRouter>

    </div>
  );
}

export default App;
