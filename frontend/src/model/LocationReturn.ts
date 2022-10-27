import {Address} from "./Address";

export type LocationReturn = {
    osm_id:string;
    lat:number;
    lon:number;
    display_name:string;
    address:Address;
}