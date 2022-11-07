import {Photo} from "../model/Photo";
import "./PhotoCard.css"

type PhotoCardProps = {
    photo:Photo;
}

export default function PhotoCard (props:PhotoCardProps){

    return(
        <div className={"photo"}>
            <img src={require(`../../../../../../../../../private/tmp/uploads/${props.photo.photoName}`)} alt={""}/>
        </div>
    )

}