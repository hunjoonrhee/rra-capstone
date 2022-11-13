import React, {ChangeEvent} from "react";
import {Link} from "react-router-dom";
import "./MainPage.css"
import DropDownMenu from "../components/DropDownMenu";

type MainPageProps={
    me:string
    getRoutesNearByLocationRequest:(locationRequest:string)=>void
    handleLogout:()=>void
    setLocation:(location:string)=>void
    location:string
}

export default function MainPage(props:MainPageProps){

    const handleLinkClick = () =>{
        props.getRoutesNearByLocationRequest(props.location);
    }
    function handleLocationChange(event:ChangeEvent<HTMLInputElement>) {
        const inputFieldValue = event.target.value;
        props.setLocation(inputFieldValue);
    }

    return(
        <div className={"mainpage"}>
            <div className={"div-dropdown-main"}>
                <DropDownMenu me={props.me} handleLogout={props.handleLogout}/>
            </div>
                <h2 id={"title"}>Running Route Advisor</h2>
                <div className={"form-searchField"}>
                    <label className={"form-input"}>
                        <input type="text" className="form-control" placeholder="Where do you want to run?" name = "location"
                               aria-label="Recipient's username" aria-describedby="button-addon2" value={props.location}
                               onChange={handleLocationChange}/>
                    </label>
                    <Link onClick={handleLinkClick} to={`/routes/${props.location}`}>
                        <button className="btn btn-outline-secondary-main" type="submit" id="button-addon2">Search</button>
                    </Link>
                </div>
        </div>
    )
}
