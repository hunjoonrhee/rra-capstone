import {Link} from "react-router-dom";
import React, {useState} from "react";
import "./RoutePage.css"
import RoutesOverview from "../components/RoutesOverview";
import useMyRoutes from "../hooks/useMyRoutes";
import DropDownMenu from "../components/DropDownMenu";
import AddNewRouteModal from "../components/AddNewRouteModal";
import L, {LatLngExpression} from "leaflet";

type RoutesPageProps={
    me:string
    saveFoundRoutes:(locationRequest:string)=>void
    setRequest:(locationRequest:string)=>void
    handleLogout:()=>void

}

export default function RoutesPage(props:RoutesPageProps){

    const {getCurrentLocation, allFoundRoutes, isClicked, setIsClicked, currentLocation,
        location, filterTag, setFilterTag, allFilter, setAllFilter, handleLocationChange, currentAddress} = useMyRoutes();

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
    const handleLinkClick = () =>{
        if(isClicked){
            props.setRequest(curAddress);
            saveFoundRoutes(curAddress);
        }else{
            props.setRequest(location);
            saveFoundRoutes(location);
        }

    }

    function saveFoundRoutes(location:string){
        props.saveFoundRoutes(location);

    }

    const hashtags: string[] = ["all", "city", "river", "street", "tree", "park"];
    function onClickHashtag(hashtag:string) {
        setFilterTag(hashtag);
        if(hashtag==="all"){
            setAllFilter(true);
        }else{
            setAllFilter(false)
        }
    }

    function handleOnClick() {

        setIsClicked(!isClicked)
        getCurrentLocation(Number(currentLocation.coordinates.lat), Number(currentLocation.coordinates.lon))
    }

    let curAddress = currentAddress.address?.road + ", " + currentAddress.address?.house_number + ", " +
        currentAddress.address?.postcode + ", " + currentAddress.address?.city;

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
                            <button className="btn btn-outline-secondary-rp"><i className="bi bi-caret-left-fill"></i> Back </button>
                        </Link>

                            { isClicked ?
                                <input type="text" className="form-control-2" placeholder="Where do you want to run?" name = "location"
                                       aria-label="Recipient's username" aria-describedby="button-addon2" value={curAddress}
                                       onChange={handleLocationChange}/>
                                :
                                <input type="text" className="form-control-2" placeholder="Where do you want to run?" name = "location"
                                       aria-label="Recipient's username" aria-describedby="button-addon2" value={location}
                                       onChange={handleLocationChange}/>
                            }
                            <button className={"btn-current-loc"} onClick={handleOnClick}><i className="bi bi-globe"></i></button>

                        <Link onClick={handleLinkClick} to={`/routes/${location}`}>
                            <button className="btn btn-outline-secondary-rp"
                                    type="submit">Search</button>
                        </Link>
            </section>
            <div className={"hashtag-band"}>
                {hashtags.map((hashtag)=> <button className={"btn-hashtag"} onClick={()=>onClickHashtag(hashtag)} key={hashtag}> {hashtag}</button>)}
            </div>
            <RoutesOverview key={allFoundRoutes.at(0)} allFoundRoutes={allFoundRoutes} filterTag={filterTag} allFilter={allFilter}/>
            <div className={"add-route"}>
                <button onClick={addANewRoute}> Add a new route</button>
            </div>
            <div>
                {
                    !isDisplay &&
                        <div>
                            <AddNewRouteModal show={addNewRouteModalOn} onHide={resetOnHide} icon={icon} currentLocation={currentLocation}/>
                        </div>
                }
            </div>

        </div>


    )
}