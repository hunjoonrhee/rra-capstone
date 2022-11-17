import {Photo} from "../model/Photo";
import "./PhotoCard.css"
import {Cloudinary} from "@cloudinary/url-gen";
import {
    AdvancedImage
} from "@cloudinary/react";
import {Route} from "../model/Route";
import {AppUser} from "../model/AppUser";

type PhotoCardProps = {
    me:AppUser | undefined
    route:Route;
    photo:Photo;
    deleteAPhotoOfRoute:(routeId:string, photoName:string)=>void
}

export default function PhotoCard (props:PhotoCardProps){

    const cld = new Cloudinary({cloud: {cloudName:"dckpphdfa"}})
    const myImage = cld.image(props.photo.name)
    function handleOnClick() {
        props.deleteAPhotoOfRoute(props.route.id!, props.photo.id)
    }
    return(


        <div className={"photo"}>
            {
                props.photo.routeId===props.route.id &&
                <>
                    {
                        props.me?.roles[0]==="ADMIN" || props.me?.username === props.photo.uploadedBy ?
                            <div style={{display: "flex", flexDirection:"column"}}>
                                <div className={"span-btn-trash-photo"}>
                                    <button className={"btn-trash-photo"} onClick={handleOnClick}><i className="bi bi-trash"></i></button>
                                </div>
                                <AdvancedImage cldImg={myImage}/>
                            </div>
                            :
                            <div style={{display: "flex", flexDirection:"column"}}>
                                <div className={"span-btn-trash-photo"}>
                                </div>
                                <AdvancedImage cldImg={myImage}/>
                            </div>

                    }
                </>
            }

        </div>

    )

}