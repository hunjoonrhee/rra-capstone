import RouteDetails from "../components/RouteDetails";
import "./RouteDetailsPage.css"
import {Route} from "../model/Route";
import {Photo} from "../model/Photo";
import {Commentary} from "../model/Commentary";
import {AppUser} from "../model/AppUser";

type RouteDetailsPageProps={
    me:AppUser | undefined
    routes:Route[];
    handleLogout:()=>void
    location:string
    deleteAPhotoOfRoute:(routeId:string, photoName:string)=>void
    getAllFoundRoutes:()=>void
    getAllPhotos:()=>void
    photos:Photo[];
    addANewCommentary:(routeId: string, user:string,newCommentary:Commentary)=>void;
    comments:Commentary[];
    deleteACommentaryOfRoute:(routeId:string, commentaryId:string)=>void;
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
                          photos={props.photos}
                          addANewCommentary={props.addANewCommentary}
                          comments={props.comments}
                          deleteACommentaryOfRoute={props.deleteACommentaryOfRoute}/>
        </div>
    )
}