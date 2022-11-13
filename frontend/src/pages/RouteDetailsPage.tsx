import RouteDetails from "../components/RouteDetails";
import "./RouteDetailsPage.css"
import {Route} from "../model/Route";
import {Photo} from "../model/Photo";

type RouteDetailsPageProps={
    me:string
    routes:Route[];
    handleLogout:()=>void
    location:string
    getPhotosOfRoute:(routeId:string | undefined)=>void
    photos:Photo[];
}

export default function RouteDetailsPage(props:RouteDetailsPageProps){


    return (
        <div>
            <RouteDetails me={props.me}
                          routes={props.routes}
                          handleLogout={props.handleLogout}
                          location={props.location}
                          getPhotosOfRoute={props.getPhotosOfRoute}
                          photos={props.photos}/>
        </div>
    )
}