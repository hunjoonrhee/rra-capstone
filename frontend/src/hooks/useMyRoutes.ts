import {useEffect, useState} from "react";
import axios from "axios";
import {toast} from "react-toastify";
import useGeoLocation from "./useGeoLocation";
import {LocationReturn} from "../model/LocationReturn";
import {Route} from "../model/Route";

export default function useMyRoutes(){

    const [location, setLocation] = useState("");
    useEffect(()=>{
        const location = JSON.parse(localStorage.getItem('location')!)
        if(location){
            setLocation(location);
        }
    }, [])
    useEffect(()=>{
        localStorage.setItem('location', JSON.stringify(location))
    }, [location])



    const [foundRoutes, setFoundRoutes] = useState([]);

    function getRoutesNearByLocationRequest(locationRequest:string){
        axios.get("/api/route/routes?address="+locationRequest)
            .then((response)=> response.data)
            .then((data)=> {setFoundRoutes(data)})
            .catch((err)=>console.log((err)))
    }

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
        axios.get("https://nominatim.openstreetmap.org/reverse?lat="+lat+"&lon="+lon+"&format=json")
            .then((response)=> {return response.data })
            .then((data)=>{setCurrentAddress(data)})
            .finally(()=>console.log("places are: ", currentAddress))
            .catch((err)=>console.log("err: ", err))
    }


    const [filterTag, setFilterTag] = useState("");
    const [allFilter, setAllFilter] = useState (true);


    function addANewRoute(newRoute:Route){
        axios.post("/api/route", newRoute)
            .then((response)=>console.log(response))
            .then(()=>toast.success("Your route is added successfully!"))
            .then(()=>console.log(location))
            .then(()=>getRoutesNearByLocationRequest(location))
    }

    function deleteARoute(routeId:string, location:string){
        console.log(location)

        axios.delete("/api/route/"+routeId + "?address="+location)
            .then((response)=>console.log(response))
            .then(()=>toast.success("The route is deleted successfully!"))
            .then(()=>console.log(location))
            .then(()=>getRoutesNearByLocationRequest(location))
    }
    const [photos, setPhotos] = useState([]);
    function getPhotosOfRoute(routeId:string | undefined){
        axios.get("/api/route/photos/"+routeId)
            .then((response)=>response.data)
            .then(data=>setPhotos(data))
            .then(err=>console.log(err))

    }


    return {foundRoutes, getCurrentLocation,getRoutesNearByLocationRequest,
        isClicked, setIsClicked, currentLocation,
        location, setLocation, filterTag, setFilterTag, allFilter, setAllFilter,
        currentAddress, addANewRoute,
        deleteARoute, getPhotosOfRoute, photos}
}