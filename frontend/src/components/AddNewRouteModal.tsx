import Modal from 'react-bootstrap/Modal';
import 'bootstrap/dist/css/bootstrap.min.css';
import "./AddNewRouteModal.css"
import React, {FormEvent, useState} from "react";
import {MapContainer, Marker, TileLayer, useMapEvents} from "react-leaflet";
import L, {LatLngExpression} from "leaflet";
import "./AddNewRouteModal.css"
import axios from "axios";
import {Route} from "../model/Route";
import useMyRoutes from "../hooks/useMyRoutes";
import {toast} from "react-toastify";

type AddNewRouteModalProps = {
    me:string;
    show:boolean;
    onHide:()=>void;
    icon:L.Icon<L.IconOptions>
    currentLocation: {loaded: boolean, coordinates: {lat: string, lon: string}}
    location:string;
}
export default function AddNewRouteModal(props:AddNewRouteModalProps) {

    const {addANewRoute} = useMyRoutes();

    const [routename, setRouteName] = useState("");
    const [hashTags, setHashTags] = useState("");
    const [startPoint, setStartPoint] = useState({lat:0, lon:0});
    const [via1Point, setVia1Point] = useState({lat:0, lon:0});
    const [via2Point, setVia2Point] = useState({lat:0, lon:0});
    const [via3Point, setVia3Point] = useState({lat:0, lon:0});
    const [endPoint, setEndPoint] = useState({lat:0, lon:0});
    const [isClickedStart, setIsClickedStart] = useState(false);
    const [isClickedVia1, setIsClickedVia1] = useState(false);
    const [isClickedVia2, setIsClickedVia2] = useState(false);
    const [isClickedVia3, setIsClickedVia3] = useState(false);
    const [isClickedEnd, setIsClickedEnd] = useState(false);
    const [selectedPosition, setSelectedPosition] = useState<[number, number]>([0,0]);

    const handleSubmit=(event:FormEvent<HTMLFormElement>)=> {
        event.preventDefault();
    }
    const locationCurrent:LatLngExpression =
        [Number(props.currentLocation.coordinates.lat),
            Number(props.currentLocation.coordinates.lon)];


    const [imageSelected, setImageSelected] = useState<File>()
    function uploadImage() {
        if (props.me !== "") {
            const formData = new FormData();
            const imgName: string | undefined = imageSelected?.name.split(".")[0]
            formData.append("file", imageSelected!)
            formData.append("public_id", imgName!);
            formData.append("upload_preset", "mo1ocdza")

            axios.post("https://api.cloudinary.com/v1_1/dckpphdfa/image/upload", formData)
                .then((response) => console.log(response))
        }
        else {
            toast('🦄 You have to log in for uploading photos!', {
                position: "top-center",
                autoClose: 5000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
                theme: "light",
            });
        }
    }


    function handleOnClickStart() {
        setIsClickedStart(!isClickedStart)
        console.log(isClickedStart)
        if(isClickedStart){
            selectedPosition ?
                setStartPoint({lat:selectedPosition[0], lon:selectedPosition[1]})
                : setStartPoint({lat:0, lon:0})
        }
    }
    function handleOnClickEnd() {
        setIsClickedEnd(!isClickedEnd)
        if(isClickedEnd){
            selectedPosition ?
                setEndPoint({lat:selectedPosition[0], lon:selectedPosition[1]})
                : setEndPoint({lat:0, lon:0})
        }
    }
    function handleOnClickVia1() {
        setIsClickedVia1(!isClickedVia1)
        if(isClickedVia1){
            selectedPosition ?
                setVia1Point({lat:selectedPosition[0], lon:selectedPosition[1]})
                : setVia1Point({lat:0, lon:0})
        }
    }
    function handleOnClickVia2() {
        setIsClickedVia2(!isClickedVia2)
        if(isClickedVia2){
            selectedPosition ?
                setVia2Point({lat:selectedPosition[0], lon:selectedPosition[1]})
                : setVia2Point({lat:0, lon:0})
        }
    }
    function handleOnClickVia3() {
        setIsClickedVia3(!isClickedVia3)
        if(isClickedVia3){
            selectedPosition ?
                setVia3Point({lat:selectedPosition[0], lon:selectedPosition[1]})
                : setVia3Point({lat:0, lon:0})
        }
    }

    const icon = L.icon({
        iconUrl:"./placeholder.png",
        iconSize: [15,15]
    })


    const Markers = () => {

        useMapEvents({
            click(e) {
                setSelectedPosition([
                    e.latlng.lat,
                    e.latlng.lng
                ]);
            },
        })

        return (
            selectedPosition ?
                <Marker
                    key={selectedPosition[0]}
                    position={selectedPosition}
                    interactive={false}
                    icon={icon}
                />
                : null
        )

    }

    let points: { lat: number; lon: number; }[] = [];
    if(via1Point.lat !== 0 && via1Point.lon !== 0 &&
        via2Point.lat !== 0 && via2Point.lon !== 0 &&
        via3Point.lat !== 0 && via3Point.lon !== 0){
        points.push(via1Point, via2Point, via3Point)
    }
    if (via1Point.lat !== 0 && via1Point.lon !== 0 &&
        via2Point.lat !== 0 && via2Point.lon !== 0 &&
        via3Point.lat === 0 && via3Point.lon === 0){
        points.push(via1Point, via2Point)
    }
    if (via1Point.lat !== 0 && via1Point.lon !== 0 &&
        via2Point.lat === 0 && via2Point.lon === 0 &&
        via3Point.lat !== 0 && via3Point.lon !== 0){
        points.push(via1Point, via3Point)
    }
    if (via1Point.lat === 0 && via1Point.lon === 0 &&
        via2Point.lat !== 0 && via2Point.lon !== 0 &&
        via3Point.lat !== 0 && via3Point.lon !== 0){
        points.push(via2Point, via3Point)
    }
    if (via1Point.lat !== 0 && via1Point.lon !== 0 &&
        via2Point.lat === 0 && via2Point.lon === 0 &&
        via3Point.lat === 0 && via3Point.lon === 0){
        points.push(via1Point)
    }
    if (via1Point.lat === 0 && via1Point.lon === 0 &&
        via2Point.lat !== 0 && via2Point.lon !== 0 &&
        via3Point.lat === 0 && via3Point.lon === 0){
        points.push(via1Point)
    }
    if (via1Point.lat === 0 && via1Point.lon === 0 &&
        via2Point.lat === 0 && via2Point.lon === 0 &&
        via3Point.lat !== 0 && via3Point.lon !== 0){
        points.push(via3Point)
    }

    // console.log(points)

    function handleOnSubmit(event:FormEvent<HTMLFormElement>) {
        event.preventDefault()
        console.log(points)
        const newRoute:Route={
            id:"",
            routeName:routename,
            hashtags: hashTags.split(", "),
            imageThumbnail: imageSelected?.name.split(".")[0],
            startPosition: startPoint,
            betweenPositions: points,
            endPosition: endPoint,
            routes:[],
            photos:[],
            createdBy:props.me
        }

        addANewRoute(newRoute);
    }

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
                    <MapContainer className={"map"} center={locationCurrent || selectedPosition}  zoom={13}
                                  style={{height:200}}
                                  >
                        <Markers />
                        <TileLayer
                            attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                            url="https://api.maptiler.com/maps/openstreetmap/256/{z}/{x}/{y}.jpg?key=Kbj8H7YVAHDxxoLRTnz3"
                        />
                    </MapContainer>
                </div>
                <form className={"form-add-route"} onSubmit={handleOnSubmit}>
                        <div className="form-group">
                            <label>Name of the route</label>
                            <input type="text" className="form-control"
                                   placeholder="Enter name of your route"
                                   value={routename}
                                   onChange={(event)=>setRouteName(event.target.value)}/>
                            <label>Hashtags</label>
                            <input type="text" className="form-control"
                                   placeholder="Hashtags for your route (break up with ', ')"
                                   value={hashTags}
                                    onChange={(event)=>setHashTags(event.target.value)}/>
                            <label>Start point</label>
                            <input type="text" className="form-control"
                                   placeholder="Choose your start point from the map"
                                   value={`${startPoint.lat}, ${startPoint.lon}`}
                                   onClick={handleOnClickStart}
                                   readOnly={true}/>
                            <label>Via...</label>
                            <input type="text" className="form-control"
                                   placeholder="Choose your start point from the map"
                                   value={`${via1Point.lat}, ${via1Point.lon}`}
                                   onClick={handleOnClickVia1}
                                   readOnly={true}/>
                            <label>Via...</label>
                            <input type="text" className="form-control"
                                   placeholder="Choose your start point from the map"
                                   value={`${via2Point.lat}, ${via2Point.lon}`}
                                   onClick={handleOnClickVia2}
                                   readOnly={true}/>
                            <label>Via...</label>
                            <input type="text" className="form-control"
                                   placeholder="Choose your start point from the map"
                                   value={`${via3Point.lat}, ${via3Point.lon}`}
                                   onClick={handleOnClickVia3}
                                   readOnly={true}/>
                            <label>End point</label>
                            <input type="text" className="form-control"
                                   placeholder="Choose your end point from the map"
                                   value={`${endPoint.lat}, ${endPoint.lon}`}
                                   onClick={handleOnClickEnd}
                                   readOnly={true}/>
                        </div>
                        <div className="input-group mb-3">
                            <div className="input-group-prepend">
                                <button className="btn btn-outline-secondary" type="button" id="inputGroupFileAddon03"
                                        onClick={uploadImage} >Upload
                                </button>
                            </div>
                            <div className="custom-file">
                                <input type="file" className="custom-file-input" id="inputGroupFile03"
                                       aria-describedby="inputGroupFileAddon03"
                                       onChange={(event)=>setImageSelected(event.target.files![0])}/>

                                <label className="custom-file-label" htmlFor="inputGroupFile03">Choose your thumbnail</label>
                            </div>
                        </div>
                        <button type="submit" className="btn btn-primary">Submit</button>
                    </form>

            </Modal.Body>
            <Modal.Footer>
                <form onSubmit={handleSubmit}>
                    <input type={"button"} onClick={props.onHide} value={"Close"}/>
                </form>
            </Modal.Footer>
        </Modal>
    )
}