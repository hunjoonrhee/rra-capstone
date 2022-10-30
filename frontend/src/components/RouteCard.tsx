import {Route} from "../model/Route";
import "./RouteCard.css"
type RouteCardProps = {
    route:Route;
}

export default function RouteCard(props:RouteCardProps){
    return(
        <div className={"route-card"}>
            <p className={"route-routename"}> {props.route.routeName}</p>
            <div>
                <img className="route-img-thumbnail" src={props.route.imageThumbnail} alt={"Thumbnail"}/>
            </div>
            {props.route.hashtags.map((hashtag)=>{
             return <span className={"hashtag"}>{hashtag}</span>
            })}
        </div>

    )
}