import {AppUser} from "./AppUser";

export type Commentary = {
    id:string;
    message:string;
    routeId:string | undefined;
    postedBy:AppUser;
    timeStamp:string;
}