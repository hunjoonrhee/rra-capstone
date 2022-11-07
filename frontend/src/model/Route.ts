import {StartPosition} from "./StartPosition";
import {Routes} from "./Routes";
import {EndPosition} from "./EndPosition";
import {Photo} from "./Photo";

export type Route = {
    id:string;
    routeName:string;
    hashtags:string[];
    imageThumbnail:string;
    startPosition:StartPosition;
    endPosition:EndPosition;
    routes:Routes[];
    photos:Photo[];
}