import {Link} from "react-router-dom";
import React from "react";
import "./RoutePage.css"
import RoutesOverview from "../components/RoutesOverview";
import Dropdown from 'react-bootstrap/Dropdown';
import useMyRoutes from "../hooks/useMyRoutes";

type RoutesPageProps={
    saveFoundRoutes:(locationRequest:string)=>void
    setRequest:(locationRequest:string)=>void
}

export default function RoutesPage(props:RoutesPageProps){

    const {getCurrentLocation, allFoundRoutes, isClicked, setIsClicked, currentLocation,
        location, filterTag, setFilterTag, allFilter, setAllFilter, handleChange, currentAddress} = useMyRoutes();

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

    return (
        <div>
            <div className={"routesPage"}>
                <div className={"dropdown"}>
                    <Dropdown>
                        <Dropdown.Toggle className={"btn-primary-main"} variant="primary" id="login-routespage">
                            <i className="fa fa-bars"></i>
                        </Dropdown.Toggle>
                        <Dropdown.Menu>
                            <Dropdown.Item href="#/sign-in">Sign in</Dropdown.Item>
                            <Dropdown.Item href="#/sign-up">Sign up</Dropdown.Item>
                        </Dropdown.Menu>
                    </Dropdown>
                </div>
                <section className={"sec-search-2"}>
                    <div >
                        <form className={"form-searchField-2"}>
                            <Link to={"/"}>
                                <button className="btn btn-outline-secondary-2"><i className="bi bi-caret-left-fill"></i> back </button>
                            </Link>

                            <div className={"form-input-2"}>
                                { isClicked ?
                                    <input type="text" className="form-control-2" placeholder="Where do you want to run?" name = "location"
                                           aria-label="Recipient's username" aria-describedby="button-addon2" value={curAddress}
                                           onChange={handleChange}/>
                                    :
                                    <input type="text" className="form-control-2" placeholder="Where do you want to run?" name = "location"
                                           aria-label="Recipient's username" aria-describedby="button-addon2" value={location}
                                           onChange={handleChange}/>
                                }
                                <button className={"btn-current-loc"} onClick={handleOnClick}><i className="bi bi-globe"></i></button>
                            </div>

                            <Link onClick={handleLinkClick} to={`/routes/${location}`}>
                                <button className="btn btn-outline-secondary-2" type="submit"
                                        >Search</button>
                            </Link>
                        </form>
                    </div>
                </section>
                <div className={"hashtag-band"}>
                    {hashtags.map((hashtag)=> <button className={"btn-hashtag"} onClick={()=>onClickHashtag(hashtag)} key={hashtag}> {hashtag}</button>)}
                </div>
                    <RoutesOverview key={allFoundRoutes.at(0)} allFoundRoutes={allFoundRoutes} filterTag={filterTag} allFilter={allFilter}/>
                {/*Todo: Ausfüllen Blabla*/}
                <div className={"blabla"}>
                    {/*<CurrentLocation currentLoc={currentLoc}/>*/}
                </div>

            </div>
        </div>

    )
}