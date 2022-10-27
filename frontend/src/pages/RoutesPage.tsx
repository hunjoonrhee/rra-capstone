import {Route} from "../model/Route";

type RoutesPageProps = {
    routes:Route[];
}

export default function RoutesPage(props:RoutesPageProps){


    const isRouteFound:boolean = props.routes.length>0;

    return (
        <div>
            {
                isRouteFound ?
                    <div>
                        {props.routes.map(
                            (route)=>{
                                return route.routeName
                            })}
                    </div>
                    :
                    <div>
                        <p> No routes found! </p>
                    </div>

            }

        </div>

    )
}