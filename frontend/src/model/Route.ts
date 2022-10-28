import {StartPosition} from "./StartPosition";

export type Route = {
    id:string;
    routeName:string;
    hashtags:string[];
    imageThumbnail:string;
    startPosition:StartPosition;
}