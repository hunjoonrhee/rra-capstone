import {Photo} from "../model/Photo";
import "./PhotoCard.css"
import {Cloudinary} from "@cloudinary/url-gen";
import {
    AdvancedImage
} from "@cloudinary/react";

type PhotoCardProps = {
    photo:Photo;
}

export default function PhotoCard (props:PhotoCardProps){

    const cld = new Cloudinary({cloud: {cloudName:"dckpphdfa"}})
    const myImage = cld.image(props.photo.name)
    // console.log(myImage)
    return(
        <div className={"photo"}>
            <AdvancedImage cldImg={myImage}/>
        </div>
    )

}