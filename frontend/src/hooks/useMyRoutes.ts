/* eslint-disable react-hooks/exhaustive-deps */
import {useEffect, useState} from "react";
import axios from "axios";
import {toast} from "react-toastify";
import useGeoLocation from "./useGeoLocation";
import {LocationReturn} from "../model/LocationReturn";
import {Route} from "../model/Route";
import {Commentary} from "../model/Commentary";

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
    const [routes, setRoutes] = useState([]);


    function getAllRoutes(){
        axios.get("/api/route")
            .then((response)=>{return response.data})
            .then((data)=>setRoutes(data))
            .catch((err)=>console.log(err))
    }

    const [foundRoutes, setFoundRoutes] = useState([]);

    function findByRoutesNear(locationRequest:string){
        axios.get("/api/route/routes?address="+locationRequest)
            .then((response)=> response.data)
            .catch((err)=>console.log((err)))
    }
    function getAllFoundRoutes(){
        if(location!==""){
            findByRoutesNear(location)
        }
        axios.get("/api/found-routes")
            .then((response)=>{return response.data})
            .then((data)=>setFoundRoutes(data))
            .catch((err)=>console.log(err))
    }

    // useEffect(()=>{
    //     getAllFoundRoutes()
    // }, [])


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
            .then(()=>getAllFoundRoutes())
    }

    function deleteARoute(routeId:string, location:string){
        axios.delete("/api/route/"+routeId + "?address="+location)
            .then((response)=>console.log(response))
            .then(()=>toast.success("The route is deleted successfully!"))
            .then(()=>getAllPhotos())
    }

    function deleteAPhotoOfRoute(routeId:string, photoId:string){
        axios.delete("/api/route/photos/"+routeId+"?photoId="+photoId)
            .then(()=>toast.success("The route is deleted successfully!"))
            .then(()=>getAllPhotos())
    }

    const [photos, setPhotos] = useState([]);
    function getAllPhotos(){
        axios.get("/api/photo/")
            .then(response=>response.data)
            .then(data=>setPhotos(data))
            .catch(err=>console.log(err));
    }

    const [comments, setComments] = useState([]);
    function getAllCommentariesOfRoute(){
        axios.get("/api/comments")
            .then(response=>response.data)
            .then(data=>setComments(data))
            .catch(err=>console.log(err));
    }

    function addANewCommentary(routeId:string, user:string, newCommentary:Commentary){
        axios.post("api/comments/"+ routeId +"?user="+user, newCommentary)
            .then((response)=>console.log(response))
            .then(()=>toast.success("Your commentary is added successfully!"))
            .then(()=>getAllCommentariesOfRoute())
    }

    function deleteACommentaryOfRoute(routeId:string, commentaryId:string){
        axios.delete("api/route/comments/"+routeId+"?commentaryId="+commentaryId)
            .then(()=>toast.success("Your commentary is deleted successfully!"))
            .then(()=>getAllCommentariesOfRoute())
    }



    return {foundRoutes, getCurrentLocation,getAllFoundRoutes,getAllRoutes,
        isClicked, setIsClicked, currentLocation, routes,addANewCommentary, getAllCommentariesOfRoute,
        location, setLocation, filterTag, setFilterTag, allFilter, setAllFilter, comments,
        currentAddress, addANewRoute,getAllPhotos, photos,
        deleteARoute, deleteAPhotoOfRoute, deleteACommentaryOfRoute}
}