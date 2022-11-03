import RouteDetails from "../components/RouteDetails";
import {useEffect, useState} from "react";
import axios from "axios";
import "./RouteDetailsPage.css"


export default function RouteDetailsPage(){
    const [routes, setRoutes] = useState([]);

    useEffect(()=>{
        getAllRoutes()
    }, [])

    function getAllRoutes(){
        axios.get("/api/route/")
            .then((response)=> {return response.data})
            .then((data)=>setRoutes(data))
            .catch((err)=>console.log((err)))
    }


    return (
        <div className={"detail-page"}>
            <RouteDetails routes={routes}/>
        </div>
    )
}