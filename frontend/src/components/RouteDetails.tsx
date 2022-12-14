import {Route} from "../model/Route";
import {useNavigate, useParams} from "react-router-dom";
import "./RouteDetails.css"
import L, {LatLngExpression} from "leaflet";
import {MapContainer, Marker, Popup, TileLayer, useMap} from "react-leaflet";
import "leaflet/dist/leaflet.css"
import React, {useState} from "react";
import axios from "axios";
import {toast} from "react-toastify";
import PhotoCard from "./PhotoCard";
import DropDownMenu from "./DropDownMenu";
import {Photo} from "../model/Photo";
import Commentaries from "./Commentaries";
import {Commentary} from "../model/Commentary";
import {AppUser} from "../model/AppUser";

type RouteDetailsProps = {
    me:AppUser | undefined
    routes:Route[];
    handleLogout:()=>void
    location:string
    deleteAPhotoOfRoute:(routeId:string, photoName:string)=>void
    getAllFoundRoutes:()=>void
    getAllPhotos:()=>void
    photos:Photo[];
    addANewCommentary:(routeId:string, user:string, newCommentary:Commentary)=>void;
    comments:Commentary[];
    deleteACommentaryOfRoute:(routeId:string, commentaryId:string)=>void;
}

export default function RouteDetails(props:RouteDetailsProps){
    const navigate = useNavigate();

    const [imageSelected, setImageSelected] = useState<File>()
    const params = useParams();
    const id = params.id;

    if(id===undefined){
        return <>Id is not defined!</>
    }

    const route = props.routes.find(route=>route.id===id);

    if(route=== undefined){
        return <></>
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
        [Number(route.startPosition.lat), Number(route.startPosition.lon)];
    const locationEnd:LatLngExpression =
        [Number(route.endPosition.lat), Number(route.endPosition.lon)];
    const iconEnd = L.icon({
        iconUrl:"./placeholder.png",
        iconSize: [15,15]
    })
    const iconStart = L.icon({
        iconUrl:"./startIcon.png",
        iconSize: [20,20]
    })

    function uploadImage() {
        if(props.me !==undefined){
            const formData = new FormData();
            const imgName:string|undefined = imageSelected?.name.split(".")[0]
            formData.append("file", imageSelected!)
            formData.append("public_id", imgName!);
            formData.append("upload_preset", "mo1ocdza")
            axios.post("https://api.cloudinary.com/v1_1/dckpphdfa/image/upload", formData)
                .then((response)=>console.log(response))

            const newPhoto:Photo = {
                id:"",
                name:imgName,
                uploadedBy:props.me.username,
                routeId:route!.id
            }

            axios.post("/api/route/photos/"+route!.id, newPhoto)
                .then(()=>toast.success("Your photo uploaded successfully!"))
                .then(()=>props.getAllPhotos())
        }else{
            toast('???? You have to log in for uploading photos!', {
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

    console.log(props.photos)

    return(
        <div className={"detailPage"}>
            <div className={"div-dropdown"}>
                <button className="btn btn-outline-secondary" style={{fontSize:10}}
                onClick={()=>navigate(-1)}><i className="bi bi-caret-left-fill"></i> Back</button>
                <DropDownMenu me={props.me} handleLogout={props.handleLogout}/>

            </div>
            <div className={"title-border"}>
                <h1 className={"title-routeName"}>{route.routeName}</h1>
            </div>
            {/*<section className={"sec-route-detail"}>*/}
                <div className={"div-map"}>
                    <MapContainer className={"map"} center={locationStart} zoom={13}>
                        <TileLayer
                            attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                            url="https://api.maptiler.com/maps/openstreetmap/256/{z}/{x}/{y}.jpg?key=Kbj8H7YVAHDxxoLRTnz3"
                        />
                        <MakeRoute id={route.id} routes={route.routes} routeName={route.routeName} imageThumbnail={route.imageThumbnail}
                                   hashtags={route.hashtags} startPosition={route.startPosition} betweenPositions={route.betweenPositions} endPosition={route.endPosition} photos={route.photos} createdBy={route.createdBy}/>
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
            {/*</section>*/}
            <div className={"photos-share"}>
                <p className={"text-photos-share"}>Share some photos for this route!</p>
            </div>
            <div className={"div-images"}>
                {
                    props.photos &&
                    props.photos.map((photo)=>{
                        return <PhotoCard key={photo.id} me={props.me} route={route} photo={photo} deleteAPhotoOfRoute={props.deleteAPhotoOfRoute}/>
                    })
                }
            </div>
            <div className="input-group mb-3" id={"input-in-detail"}>
                <div className="input-group-prepend">
                    <button className="btn btn-outline-secondary" type="button" id="inputGroupFileAddon03"
                            style={{color:"white"}}
                            onClick={uploadImage}>Upload
                    </button>
                </div>
                <div className="custom-file">
                    <input type="file" className="custom-file-input" id="inputGroupFile03"
                           aria-describedby="inputGroupFileAddon03"
                           onChange={(event)=>setImageSelected(event.target.files![0])}/>

                    <label className="custom-file-label" htmlFor="inputGroupFile03">Choose file</label>
                </div>
            </div>

            <section className={"sec-comments"}>
                <Commentaries addANewCommentary={props.addANewCommentary}
                              route={route} me={props.me}
                              comments={props.comments}
                              deleteACommentaryOfRoute={props.deleteACommentaryOfRoute}/>
            </section>

        </div>

    )
}