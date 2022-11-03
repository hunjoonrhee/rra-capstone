import {Route} from "../model/Route";
import {useParams} from "react-router-dom";
import "./RouteDetails.css"
import L, {LatLngExpression} from "leaflet";
import {MapContainer, Marker, Popup, TileLayer, useMap} from "react-leaflet";
import "leaflet/dist/leaflet.css"
import Dropdown from "react-bootstrap/Dropdown";
import React from "react";


type RouteDetailsProps = {
    routes:Route[];
}

export default function RouteDetails(props:RouteDetailsProps){

    const params = useParams();
    const id = params.id;

    if(id===undefined){
        return <>Id is not defined!</>
    }

    const route = props.routes.find(route=>route.id===id);

    if(route=== undefined){
        return <>Route was not found!</>
    }

    function MakeRoute(route:Route){
        const map = useMap();
        L.geoJSON(route.routes[0].geometry).addTo(map);
        return null;
    }


    function ResetCenterView(){

        const map = useMap();
        map.setView(
            L.latLng(Number(route!.startPosition.lat), Number(route!.startPosition.lon)),
            map.getZoom(),{animate:true}
        )

        return null;
    }
    const locationStart:LatLngExpression =
        [Number(route!.startPosition.lat), Number(route!.startPosition.lon)];
    const locationEnd:LatLngExpression =
        [Number(route!.endPosition.lat), Number(route!.endPosition.lon)];
    const iconEnd = L.icon({
        iconUrl:"./placeholder.png",
        iconSize: [15,15]
    })
    const iconStart = L.icon({
        iconUrl:"./startIcon.png",
        iconSize: [20,20]
    })


    return(
        <div>
            <div className={"dropdown-detailpage"}>
                <Dropdown>
                    <Dropdown.Toggle className={"btn-primary-detailpage"} variant="primary" id="login-routespage">
                        <i className="fa fa-bars"></i>
                    </Dropdown.Toggle>
                    <Dropdown.Menu>
                        <Dropdown.Item href="#/sign-in">Sign in</Dropdown.Item>
                        <Dropdown.Item href="#/sign-up">Sign up</Dropdown.Item>
                    </Dropdown.Menu>
                </Dropdown>
            </div>
            <section className={"title-route-detail"}>
                <div className={"title-border"}>
                    <p className={"title-routeName"}>{route.routeName}</p>
                </div>
            </section>
            <section className={"sec-route-detail"}>
                <div className={"div-map"}>
                    <MapContainer className={"map"} center={locationStart} zoom={13}>
                        <TileLayer
                            attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                            url="https://api.maptiler.com/maps/openstreetmap/256/{z}/{x}/{y}.jpg?key=Kbj8H7YVAHDxxoLRTnz3"
                        />
                        <MakeRoute id={route.id} routes={route.routes} routeName={route.routeName} imageThumbnail={route.imageThumbnail}
                                   hashtags={route.hashtags} startPosition={route.startPosition} endPosition={route.endPosition}/>
                        <Marker position={locationStart} icon={iconStart}>
                            <Popup>
                                You can start here!
                            </Popup>
                        </Marker>
                        <Marker position={locationEnd} icon={iconEnd}>
                            <Popup>
                                You can stop here!
                            </Popup>
                        </Marker>

                        <ResetCenterView/>
                    </MapContainer>
                </div>

            </section>
        </div>

    )
}