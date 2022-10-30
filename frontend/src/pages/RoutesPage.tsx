import {Link} from "react-router-dom";
import React, {ChangeEvent, useEffect, useState} from "react";
import "./RoutePage.css"
import axios from "axios";
import RoutesOverview from "../components/RoutesOverview";

type RoutesPageProps={
    saveFoundRoutes:(locationRequest:string)=>void
    setRequest:(locationRequest:string)=>void
}

export default function RoutesPage(props:RoutesPageProps){

    const [allFoundRoutes, setAllFoundRoutes] = useState([]);

    useEffect(()=>{
        getAllFoundRoutes()
    }, [])

    function getAllFoundRoutes(){
        axios.get("/api/found-routes/")
            .then((response)=> {return response.data})
            .then((data)=>setAllFoundRoutes(data))
            .catch((err)=>console.log((err)))
    }


    const [location, setLocation] = useState("");
    const [filterTag, setFilterTag] = useState("");
    const [allFilter, setAllFilter] = useState (true);

    function handleChange(event:ChangeEvent<HTMLInputElement>) {
        const inputFieldValue = event.target.value;
        setLocation(inputFieldValue);
    }

    const handleLinkClick = () =>{
        props.setRequest(location);
        saveFoundRoutes(location);
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

    return (
        <div>
            <div className={"routesPage"}>
                <section className={"sec-search-2"}>
                    <div >
                        <form className={"form-searchField-2"}>
                            <Link to={"/"}>
                                <button className="btn btn-outline-secondary-2"><i className="bi bi-caret-left-fill"></i> back </button>
                            </Link>

                            <label className={"form-input-2"}>
                                <input type="text" className="form-control-2" placeholder="Where do you want to run?" name = "location"
                                       aria-label="Recipient's username" aria-describedby="button-addon2" value={location}
                                       onChange={handleChange}/>
                            </label>

                            <Link onClick={handleLinkClick} to={`/routes/${location}`}>
                                <button className="btn btn-outline-secondary-2" type="submit"
                                        >Search</button>
                            </Link>
                        </form>
                    </div>
                </section>
                <div className={"hashtag-band"}>
                    {hashtags.map((hashtag)=> <button className={"btn-hashtag"} onClick={()=>onClickHashtag(hashtag)}> {hashtag}</button>)}
                </div>
                    <RoutesOverview key={allFoundRoutes.at(0)} allFoundRoutes={allFoundRoutes} filterTag={filterTag} allFilter={allFilter}/>
                {/*Todo: Ausfüllen Blabla*/}
                <div className={"blabla"}>
                    {/*Todo: Ausfüllen Blabla*/}
                </div>

            </div>
        </div>

    )
}