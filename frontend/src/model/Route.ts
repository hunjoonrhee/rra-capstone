import {StartPosition} from "./StartPosition";
import {Routes} from "./Routes";
import {EndPosition} from "./EndPosition";
import {Photo} from "./Photo";
import {Position} from "./Position";

export type Route = {
    id?:string;
    routeName:string;
    hashtags:string[];
    imageThumbnail:string | undefined;
    startPosition:StartPosition;
    betweenPositions:Position[];
    endPosition:EndPosition;
    routes:Routes[];
    photos:Photo[];
    createdBy:string | undefined;
}