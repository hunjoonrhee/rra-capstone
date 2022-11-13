import {Link} from "react-router-dom";
import React, {ChangeEvent, useState} from "react";
import "./RoutePage.css"
import RoutesOverview from "../components/RoutesOverview";
import DropDownMenu from "../components/DropDownMenu";
import AddNewRouteModal from "../components/AddNewRouteModal";
import L from "leaflet";
import useGeoLocation from "../hooks/useGeoLocation";
import {LocationReturn} from "../model/LocationReturn";
import {Route} from "../model/Route";

type RoutesPageProps={
    me:string
    handleLogout:()=>void
    setFilterTag:(hashtag:string)=>void
    setAllFilter:(a:boolean)=>void
    getCurrentLocation:(lat:number, lon:number)=>void
    currentAddress:LocationReturn
    getRoutesNearByLocationRequest:(locationRequest:string)=>void
    filterTag:string
    foundRoutes:Route[];
    deleteARoute:(routeId:string, location:string)=>void
    allFilter:boolean
    setLocation:(location:string)=>void
    location:string
    getPhotosOfRoute:(routeId:string | undefined)=>void
}

export default function RoutesPage(props:RoutesPageProps){

    const currentLocation = useGeoLocation();

    const [isDisplay, setIsDisplay] = useState(true);
    const [addNewRouteModalOn, setAddNewRouteModalOn] = useState(false);

    const addANewRoute = () => {
        setIsDisplay(!isDisplay);
        setAddNewRouteModalOn(true);
    };
    const resetOnHide = () =>{
        setAddNewRouteModalOn(false);
        setIsDisplay(!isDisplay);
    }

    const [isClicked, setIsClicked] = useState(false);


    function currentLocationOnClick() {

        setIsClicked(!isClicked)
        props.getCurrentLocation(Number(currentLocation.coordinates.lat), Number(currentLocation.coordinates.lon))
    }

    let curAddress = props.currentAddress.address?.road + ", " + props.currentAddress.address?.house_number + ", " +
        props.currentAddress.address?.postcode + ", " + props.currentAddress.address?.city;


    const handleLinkClick = () =>{
        if(isClicked){
            props.getRoutesNearByLocationRequest(curAddress);
        }else{
            console.log(props.location)
            props.getRoutesNearByLocationRequest(props.location);
        }

    }
    function handleLocationChange(event:ChangeEvent<HTMLInputElement>) {

        const inputFieldValue = event.target.value;
        if(isClicked){
            props.getRoutesNearByLocationRequest(curAddress);
        }else{
            props.setLocation(inputFieldValue);
        }
    }



    const hashtags: string[] = ["all", "city", "river", "street", "tree", "park"];
    function onClickHashtag(hashtag:string) {
        props.setFilterTag(hashtag);
        if(hashtag==="all"){
            props.setAllFilter(true);
        }else{
            props.setAllFilter(false)
        }
    }



    const icon = L.icon({
        iconUrl:"./placeholder.png",
        iconSize: [15,15]
    })


    return (

        <div className={"routesPage"}>
            <div className={"div-dropdown-1"}>
                <DropDownMenu me={props.me} handleLogout={props.handleLogout}/>
            </div>
            <section className={"sec-search-2"}>
                        <Link to={"/"}>
                            <button className="btn btn-outline-secondary" style={{fontSize: 10}}><i className="bi bi-caret-left-fill"></i> Back </button>
                        </Link>

                            { isClicked ?
                                <input type="text" className="form-control-2" placeholder={props.location} name = "location"
                                       aria-label="Recipient's username" aria-describedby="button-addon2" value={curAddress}
                                       />
                                :
                                <input type="text" className="form-control-2" placeholder={props.location} name = "location"
                                       aria-label="Recipient's username" aria-describedby="button-addon2" value={props.location}
                                       onChange={handleLocationChange}/>
                            }
                            <button className={"btn-current-loc"} onClick={currentLocationOnClick}><i className="bi bi-globe"></i></button>

                        <Link onClick={handleLinkClick} to={`/routes/${props.location}`}>
                            <button className="btn btn-outline-secondary"
                                    type="button" style={{fontSize: 10}}>Search</button>
                        </Link>
            </section>
            <div className={"hashtag-band"}>
                {hashtags.map((hashtag)=> <button className={"btn-hashtag"} onClick={()=>onClickHashtag(hashtag)} key={hashtag}> {hashtag}</button>)}
            </div>
            <RoutesOverview me={props.me} foundRoutes={props.foundRoutes}
                            filterTag={props.filterTag} allFilter={props.allFilter}
                            deleteARoute={props.deleteARoute} location={props.location}
                            getPhotosOfRoute={props.getPhotosOfRoute}/>
            <div className={"add-route"}>
                <button onClick={addANewRoute}> Add a new route</button>
            </div>
            <div>
                {
                    !isDisplay &&
                        <div>
                            <AddNewRouteModal me={props.me} location={props.location} show={addNewRouteModalOn} onHide={resetOnHide} icon={icon} currentLocation={currentLocation}/>
                        </div>
                }
            </div>

        </div>


    )
}