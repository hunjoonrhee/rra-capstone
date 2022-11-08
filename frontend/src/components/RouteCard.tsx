import { Link } from "react-router-dom";
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
                <Link to={`/route/${props.route.id}/details`}>
                    <img className="route-img-thumbnail" src={props.route.imageThumbnail} alt={"Thumbnail"}/>
                </Link>

            </div>
            {props.route.hashtags.map((hashtag)=>{
             return <span className={"hashtag"} key={hashtag}>{hashtag}</span>
            })}
        </div>

    )
}