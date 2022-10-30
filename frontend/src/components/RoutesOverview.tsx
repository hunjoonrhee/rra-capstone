import RouteCard from "./RouteCard";
import React from "react";
import {FoundRoutes} from "../model/FoundRoutes";
import {useParams} from "react-router-dom";
import "./RoutesOverview.css"

type RoutesOverviewProps = {
    allFoundRoutes:FoundRoutes[];
    filterTag:string;
    allFilter:boolean;
}
export default function RoutesOverview(props:RoutesOverviewProps){
    console.log(props.allFilter)
    const params = useParams();
    const id = params.id;

    console.log(id);

    if(id===undefined){
        return <>Address is not defined!</>
    }

    const foundRoutes = props.allFoundRoutes.find(foundRoutes=>foundRoutes.id === id);
    if(foundRoutes=== undefined){
        return <>No Route was found!</>
    }

    const filteredRoutes = foundRoutes.routes.filter(route => route.hashtags.includes(props.filterTag))

    return(
        <section className={"sec-routes-overview"}>
            {
                !props.allFilter ?
                    <>
                        {filteredRoutes.map(
                            (route)=>{
                                return <RouteCard key={route.id} route={route}/>
                            }
                        )}
                    </>
                    :
                    <>
                        {foundRoutes.routes.map(
                            (route)=>{
                                return <RouteCard key={route.id} route={route}/>
                            }
                        )}
                    </>
            }

        </section>

    )
}