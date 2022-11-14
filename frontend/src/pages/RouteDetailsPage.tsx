import RouteDetails from "../components/RouteDetails";
import "./RouteDetailsPage.css"
import {Route} from "../model/Route";
import {Photo} from "../model/Photo";

type RouteDetailsPageProps={
    me:string
    routes:Route[];
    handleLogout:()=>void
    location:string
    deleteAPhotoOfRoute:(routeId:string, photoName:string)=>void
    getAllFoundRoutes:()=>void
    getAllPhotos:()=>void
    photos:Photo[];

}

export default function RouteDetailsPage(props:RouteDetailsPageProps){


    return (
        <div>
            <RouteDetails me={props.me}
                          routes={props.routes}
                          handleLogout={props.handleLogout}
                          location={props.location}
                          deleteAPhotoOfRoute={props.deleteAPhotoOfRoute}
                          getAllFoundRoutes={props.getAllFoundRoutes}
                          getAllPhotos={props.getAllPhotos}
                          photos={props.photos}/>
        </div>
    )
}