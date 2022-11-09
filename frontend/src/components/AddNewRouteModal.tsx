import Modal from 'react-bootstrap/Modal';
import 'bootstrap/dist/css/bootstrap.min.css';
import {Col, Form, Row} from "react-bootstrap";
import "./AddNewRouteModal.css"
import React, {ChangeEvent, FormEvent, useState} from "react";
import {MapContainer, Marker, Popup, TileLayer, useMap} from "react-leaflet";
import L, {LatLngExpression} from "leaflet";
import "./AddNewRouteModal.css"
type AddNewRouteModalProps = {
    show:boolean;
    onHide:()=>void;
    icon:L.Icon<L.IconOptions>
    currentLocation: {loaded: boolean, coordinates: {lat: string, lon: string}}
}
export default function AddNewRouteModal(props:AddNewRouteModalProps) {


    const handleSubmit=(event:FormEvent<HTMLFormElement>)=> {
        event.preventDefault();
    }
    function ResetCenterView(){

        const map = useMap();
        map.setView(
            L.latLng(Number(props.currentLocation.coordinates.lat),
                Number(props.currentLocation.coordinates.lon)),
            map.getZoom(),{animate:true}
        )

        return null;
    }
    const locationCurrent:LatLngExpression =
        [Number(props.currentLocation.coordinates.lat),
            Number(props.currentLocation.coordinates.lon)];

    return(
        <Modal show={props.show} animation={true}
               size="xl"
               aria-labelledby="contained-modal-title-vcenter"
               centered>
            <Modal.Header>
                <Modal.Title>Add your own route!</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <div className={"div-map-modal"}>
                    <MapContainer className={"map"} center={locationCurrent} zoom={13}
                                  style={{height:200}}>
                        <TileLayer
                            attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                            url="https://api.maptiler.com/maps/openstreetmap/256/{z}/{x}/{y}.jpg?key=Kbj8H7YVAHDxxoLRTnz3"
                        />
                        <Marker position={locationCurrent} icon={props.icon}>
                            <Popup>
                                You can start here!
                            </Popup>
                        </Marker>


                        <ResetCenterView/>
                    </MapContainer>
                </div>
            </Modal.Body>
            <Modal.Footer>

                <form onSubmit={handleSubmit}>
                    <input type={"button"} onClick={props.onHide} value={"Close"}/>

                </form>
            </Modal.Footer>
        </Modal>
    )
}

/* for 37 <input className={"btn-save"} type={"submit"} value={"Save Changes"}/>*/