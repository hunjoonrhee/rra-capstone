import React, {ChangeEvent, useState} from "react";
import {Link} from "react-router-dom";
import "./MainPage.css"

type MainPageProps={
    setRequest:(locationRequest:string)=>void
    getRoutesNearByLocationRequest:(locationRequest:string)=>void
}

export default function MainPage(props:MainPageProps){

    const [location, setLocation] = useState("");
    function handleChange(event:ChangeEvent<HTMLInputElement>) {
        const inputFieldValue = event.target.value;
        setLocation(inputFieldValue);
    }

    const handleLinkClick = () =>{
        console.log("button is clicked!")
        props.setRequest(location)
        props.getRoutesNearByLocationRequest(location);
    }

    return(
        <div className={"mainpage"}>
            <section className={"sec-title"}>
                <h2 id={"title"}>Running Route Advisor</h2>
            </section>
            <section className={"sec-search"}>
                <div className={"form-searchField"}>
                    <form>
                        <label className={"form-input"}>
                            <input type="text" className="form-control" placeholder="Where do you want to run?" name = "location"
                                   aria-label="Recipient's username" aria-describedby="button-addon2" value={location}
                                   onChange={handleChange}/>
                        </label>
                        <Link onClick={handleLinkClick} to={"/routes"}>
                            <button className="btn btn-outline-secondary" type="submit" id="button-addon2">Search</button>
                        </Link>
                    </form>
                </div>
            </section>
        </div>
    )
}