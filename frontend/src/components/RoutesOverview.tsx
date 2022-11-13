import RouteCard from "./RouteCard";
import React from "react";
import {useParams} from "react-router-dom";
import "./RoutesOverview.css"
import {Route} from "../model/Route";

type RoutesOverviewProps = {
    me:string;
    foundRoutes:Route[];
    filterTag:string;
    allFilter:boolean;
    deleteARoute:(routeId:string, location:string) => void
    location:string;
    getPhotosOfRoute:(routeId:string | undefined)=>void
}
export default function RoutesOverview(props:RoutesOverviewProps){
    const params = useParams();
    const id = params.id;

    console.log(id)

    if(id===undefined){
        return <>Address is not defined!</>
    }

    // const foundRoutes = props.foundRoutes.find(foundRoutes=>foundRoutes.id === id);
    // if(foundRoutes=== undefined){
    //     return <>No Route was found!</>
    // }
    console.log(props.foundRoutes)

    const filteredRoutes = props.foundRoutes.filter(route => route.hashtags.includes(props.filterTag))

    return(
        <section className={"sec-routes-overview"}>
            {
                !props.allFilter ?
                    <>
                        {filteredRoutes.map(
                            (route)=>{
                                return <RouteCard key={route.id} me={props.me} route={route}
                                                  deleteARoute={props.deleteARoute} location={props.location}
                                                  getPhotosOfRoute={props.getPhotosOfRoute}
                                />
                            }
                        )}
                    </>
                    :
                    <>
                        {props.foundRoutes.map(
                            (route)=>{
                                return <RouteCard key={route.id} me={props.me} route={route}
                                                  deleteARoute={props.deleteARoute} location={props.location}
                                                  getPhotosOfRoute={props.getPhotosOfRoute}

                                />
                            }
                        )}
                    </>
            }

        </section>

    )
}