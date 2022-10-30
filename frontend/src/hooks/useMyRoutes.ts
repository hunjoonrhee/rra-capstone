import {useEffect, useState} from "react";
import axios from "axios";

export default function useMyRoutes(){

    const [locationRequest, setLocationRequest] = useState("");

    function setRequest(locationRequest:string){
        setLocationRequest(locationRequest);
    }

    const [foundRoutes, setFoundRoutes] = useState([]);


    function getRoutesNearByLocationRequest(locationRequest:string){
        axios.get("/api/found-routes/"+locationRequest)
            .then((response)=> response.data)
            .then((data)=> {setFoundRoutes(data)})
            .catch((err)=>console.log((err)))
    }


    useEffect(()=>{
        if(sessionStorage.getItem('my-key')){
            setRequest(sessionStorage.getItem('my-key')!)
        }
    }, [])
    useEffect(()=>{
        sessionStorage.setItem('my-key', locationRequest)
    }, [locationRequest])

    function saveFoundRoutes(locationRequest:string){
        if(locationRequest.length>0){
            axios.post("/api/found-routes?address="+locationRequest)
                .then(()=>getRoutesNearByLocationRequest(locationRequest))
                .catch((err)=>console.log(err))
        }
        // TODO: tostify für else
    }



    return {setRequest, foundRoutes, saveFoundRoutes}
}