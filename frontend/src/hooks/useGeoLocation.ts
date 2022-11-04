import {useEffect, useState} from "react";

export default function useGeoLocation(){

    const [currentLocation, setCurrentLocation] = useState({
        loaded: false,
        // address:{street:"", house_number:"", postcode:"", city:""}
        coordinates: { lat:"", lon:""},
    });

    const onSuccess = (currentLocation: { coords: { latitude: any; longitude: any; }; }) => {
        setCurrentLocation({
            loaded:true,
            // address:{}
            coordinates: {
                lat:currentLocation.coords.latitude,
                lon:currentLocation.coords.longitude
            },
        });
    };

    useEffect(()=>{
        if(!("geolocation" in navigator)){
            alert({
                code:0,
                message:"Geolocation not supported",
            })

        }

        navigator.geolocation.getCurrentPosition(onSuccess);
    }, [])

    return currentLocation;
}