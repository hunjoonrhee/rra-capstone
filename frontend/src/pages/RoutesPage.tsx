import {Route} from "../model/Route";
import {Link} from "react-router-dom";
import React, {ChangeEvent, useState} from "react";
import "./RoutePage.css"

type RoutesPageProps = {
    routes:Route[];
    setRequest:(locationRequest:string)=>void
    getRoutesNearByLocationRequest:(locationRequest:string)=>void
}

export default function RoutesPage(props:RoutesPageProps){
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

    const isRouteFound:boolean = props.routes.length>0;

    return (
        <div>
            {
                isRouteFound ?
                    <div className={"routesPage"}>
                        <section className={"sec-search-2"}>
                            <div className={"form-searchField-2"}>
                                <form>
                                    <label className={"form-input-2"}>
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
                        <section className={"sec-routes"}>
                            {props.routes.map(
                                (route)=>{
                                    return route.routeName
                                })}
                        </section>

                    </div>
                    :
                    <div>
                        <p> No routes found! </p>
                    </div>

            }

        </div>

    )
}