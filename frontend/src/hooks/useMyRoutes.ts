import {useEffect, useState} from "react";
import axios from "axios";

export default function useMyRoutes(){
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
    }, [locationRequest])
    useEffect(()=>{
        if(sessionStorage.getItem('my-key')){
            setRequest(sessionStorage.getItem('my-key')!)
        }
    }, [])
    useEffect(()=>{
        sessionStorage.setItem('my-key', locationRequest);
    }, [locationRequest])

    return {setRequest, routes, getRoutesNearByLocationRequest}
}