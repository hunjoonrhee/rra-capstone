export type LocationReturn = {
    address:{
        city:string | undefined;
        country_code:string | undefined;
        postcode:string | undefined;
        road:string| undefined;
        state:string| undefined;
        suburb:string| undefined;
    } |undefined;
    osm_id:number | undefined;
    display_name:string | undefined;
    lat:string | undefined;
    lon:string | undefined;
}