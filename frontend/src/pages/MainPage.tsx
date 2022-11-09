import React, {ChangeEvent} from "react";
import {Link} from "react-router-dom";
import "./MainPage.css"
import DropDownMenu from "../components/DropDownMenu";

type MainPageProps={
    me:string
    saveFoundRoutes:(locationRequest:string)=>void
    setRequest:(locationRequest:string)=>void
    handleLogout:()=>void
    location:string
    handleLocationChange:(event:ChangeEvent<HTMLInputElement>)=>void
}

export default function MainPage(props:MainPageProps){

    const handleLinkClick = () =>{
        props.setRequest(props.location);
        saveFoundRoutes(props.location);
    }

    function saveFoundRoutes(location:string){
        props.saveFoundRoutes(location);
    }

    return(
        <div className={"mainpage"}>
            <div className={"div-dropdown-main"}>
                <DropDownMenu me={props.me} handleLogout={props.handleLogout}/>
            </div>
            <section className={"sec-title"}>
                <h2 id={"title"}>Running Route Advisor</h2>
            </section>
            <section className={"sec-search"}>
                <div className={"form-searchField"}>
                    <label className={"form-input"}>
                        <input type="text" className="form-control" placeholder="Where do you want to run?" name = "location"
                               aria-label="Recipient's username" aria-describedby="button-addon2" value={props.location}
                               onChange={props.handleLocationChange}/>
                    </label>
                    <Link onClick={handleLinkClick} to={`/routes/${props.location}`}>
                        <button className="btn btn-outline-secondary-main" type="submit" id="button-addon2">Search</button>
                    </Link>
                </div>
            </section>
        </div>
    )
}
