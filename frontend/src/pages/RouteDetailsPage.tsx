import RouteDetails from "../components/RouteDetails";
import "./RouteDetailsPage.css"
import {Route} from "../model/Route";

type RouteDetailsPageProps={
    me:string
    routes:Route[];
    handleLogout:()=>void
    location:string
}

export default function RouteDetailsPage(props:RouteDetailsPageProps){


    return (
        <div>
            <RouteDetails me={props.me} routes={props.routes} handleLogout={props.handleLogout} location={props.location}/>
        </div>
    )
}