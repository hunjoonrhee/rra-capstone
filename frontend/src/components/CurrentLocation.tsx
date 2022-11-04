import {Address} from "../model/Address";

type CurrentLocationProps={
    currentLoc:Address;
}

export default function CurrentLocation(props:CurrentLocationProps){
    return(
        <div>
            {props.currentLoc.road}
        </div>
    )
}