import React, {ChangeEvent, FormEvent, useEffect, useState} from "react";
import {Link} from "react-router-dom";
import "./MainPage.css"

type MainPageProps={
    setRequest:(locationRequest:string)=>void
    getRoutesNearByLocationRequest:(locationRequest:string)=>void
}

export default function MainPage(props:MainPageProps){
    console.log("mainpage rendered")


    const [location, setLocation] = useState("");
    function handleChange(event:ChangeEvent<HTMLInputElement>) {
        const inputFieldValue = event.target.value;
        setLocation(inputFieldValue);
        console.log(location)

    }

    const handleLinkClick = () =>{
        // event.preventDefault();
        console.log("button is clicked!")
        props.setRequest(location)
        console.log(location)
        props.getRoutesNearByLocationRequest(location);
        // setLocation("");
    }

    return(
        <div className={"mainpage"}>
            <section className={"sec-title"}>
                blablabla
            </section>
            <section className={"sec-search"}>
                <form className={"form-searchField"}>

                    <label>
                        <input type="text" className="form-control" placeholder="Where do you want to run?" name = "location"
                        aria-label="Recipient's username" aria-describedby="button-addon2" value={location}
                        onChange={handleChange}/>
                    </label>
                    <Link onClick={handleLinkClick} to={"/routes"}>
                        <button className="btn btn-outline-secondary" type="submit" id="button-addon2">Search</button>
                    </Link>

                    {/*<label>*/}
                    {/*    <input className={"form-input"} name={"location"} type={"text"} value={location}*/}
                    {/*           placeholder={"Where do you want to run?"} onChange={handleChange}/>*/}
                    {/*</label>*/}
                    {/*<Link onClick={handleLinkClick} to={"/routes"}>*/}
                    {/*    <button className={"form-submit"} type={"submit"}>Search</button>*/}
                    {/*</Link>*/}
                </form>
            </section>
        </div>
    )
}