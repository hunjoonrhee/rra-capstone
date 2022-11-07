import RouteDetails from "../components/RouteDetails";
import "./RouteDetailsPage.css"
import useMyRoutes from "../hooks/useMyRoutes";


export default function RouteDetailsPage(){
    const {routes} = useMyRoutes();


    return (
        <div className={"detail-page"}>
            <RouteDetails routes={routes}/>
        </div>
    )
}