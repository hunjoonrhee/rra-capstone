import {ChangeEvent, useEffect, useState} from "react";
import axios from "axios";
import {toast} from "react-toastify";
import useGeoLocation from "./useGeoLocation";
import {LocationReturn} from "../model/LocationReturn";

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

    function toastifyByNoRequest() {
        toast.error("Please enter a location!")
    }

    function saveFoundRoutes(locationRequest:string){
        if(locationRequest.length>0){
            axios.post("/api/found-routes?address="+locationRequest)
                .then(()=>getRoutesNearByLocationRequest(locationRequest))
                .catch((err)=>console.log(err))
        }else{
            toastifyByNoRequest()
        }
    }


    const [allFoundRoutes, setAllFoundRoutes] = useState([]);
    const [isClicked, setIsClicked] = useState(false);

    const currentLocation = useGeoLocation();

    const [currentAddress, setCurrentAddress] = useState<LocationReturn>({
        address: {
            house_number:"",
            city:"",
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

    useEffect(()=>{
        getAllFoundRoutes()
    }, [])

    function getAllFoundRoutes(){
        axios.get("/api/found-routes/")
            .then((response)=> {return response.data})
            .then((data)=>setAllFoundRoutes(data))
            .catch((err)=>console.log((err)))
    }


    const [location, setLocation] = useState("");
    const [filterTag, setFilterTag] = useState("");
    const [allFilter, setAllFilter] = useState (true);

    function handleChange(event:ChangeEvent<HTMLInputElement>) {
        const inputFieldValue = event.target.value;
        setLocation(inputFieldValue);
    }

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


    return {setRequest, foundRoutes, saveFoundRoutes, getCurrentLocation, allFoundRoutes, isClicked, setIsClicked, currentLocation,
    location, filterTag, setFilterTag, allFilter, setAllFilter, handleChange,currentAddress, routes, setRoutes, getAllRoutes}
}