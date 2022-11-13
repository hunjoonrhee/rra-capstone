import { Link } from "react-router-dom";
import {Route} from "../model/Route";
import "./RouteCard.css"
import {Cloudinary} from "@cloudinary/url-gen";
import {thumbnail} from "@cloudinary/url-gen/actions/resize";
import {focusOn} from "@cloudinary/url-gen/qualifiers/gravity";
import {FocusOn} from "@cloudinary/url-gen/qualifiers/focusOn";
import {byRadius} from "@cloudinary/url-gen/actions/roundCorners";
import {AdvancedImage} from "@cloudinary/react";

type RouteCardProps = {
    me:string;
    route:Route;
    deleteARoute:(routeId:string, location:string) => void
    location:string
    getPhotosOfRoute:(routeId:string | undefined)=>void
}

export default function RouteCard(props:RouteCardProps){

    const cld = new Cloudinary({cloud: {cloudName:"dckpphdfa"}})
    const myImage = cld.image(props.route.imageThumbnail)
    myImage.resize(thumbnail().width(120).height(120).gravity(focusOn(FocusOn.face())))
        .roundCorners(byRadius(20))

    function handleOnClick() {
        console.log(props.location)
        console.log(props.route.id)
        props.deleteARoute(props.route.id!, props.location)
    }

    function onClickToDetailPage() {
        props.getPhotosOfRoute(props.route.id)
    }

    return(
        <div className={"route-card"}>
            {
                props.me==="admin" ?
                <div className={"title-routecard"}>
                    <p className={"route-routename"}> {props.route.routeName}</p>
                    <button className={"btn-trash"} onClick={handleOnClick}><i className="bi bi-trash"></i></button>
                </div>
                    :
                props.me === "" || props.me!==props.route.createdBy ?
                    <p className={"route-routename"}> {props.route.routeName}</p>
                    :
                        props.me === props.route.createdBy &&
                        <div className={"title-routecard"}>
                            <p className={"route-routename"}> {props.route.routeName}</p>
                            <button className={"btn-trash"} onClick={handleOnClick}><i className="bi bi-trash"></i></button>
                        </div>
            }

            <div>
                <Link onClick={onClickToDetailPage} to={`/route/${props.route.id}/details`}>
                    <AdvancedImage cldImg={myImage} />
                </Link>

            </div>
            {props.route.hashtags.map((hashtag)=>{
             return <span className={"hashtag"} key={hashtag}>{hashtag}</span>
            })}
        </div>

    )
}