import RouteCard from "./RouteCard";
import React from "react";
import {useParams} from "react-router-dom";
import "./RoutesOverview.css"
import {FoundRoutes} from "../model/FoundRoutes";

type RoutesOverviewProps = {
    me:string;
    foundRoutes:FoundRoutes[];
    filterTag:string;
    allFilter:boolean;
    deleteARoute:(routeId:string, location:string) => void
    location:string;
}
export default function RoutesOverview(props:RoutesOverviewProps){
    console.log(props.foundRoutes)
    const params = useParams();
    const id = params.id;

    if(id===undefined){
        return <>Address is not defined!</>
    }

    const foundRoutes = props.foundRoutes.find(foundRoutes=>foundRoutes.id === id);
    if(foundRoutes=== undefined){
        return <></>
    }
    console.log(foundRoutes)

    const filteredRoutes = foundRoutes.routes.filter(route => route.hashtags.includes(props.filterTag))

    return(
        <section className={"sec-routes-overview"}>
            {
                !props.allFilter ?
                    <>
                        {filteredRoutes.map(
                            (route)=>{
                                return <RouteCard key={route.id} me={props.me} route={route}
                                                  deleteARoute={props.deleteARoute} location={props.location}

                                />
                            }
                        )}
                    </>
                    :
                    <>
                        {foundRoutes.routes.map(
                            (route)=>{
                                return <RouteCard key={route.id} me={props.me} route={route}
                                                  deleteARoute={props.deleteARoute} location={props.location}


                                />
                            }
                        )}
                    </>
            }

        </section>

    )
}