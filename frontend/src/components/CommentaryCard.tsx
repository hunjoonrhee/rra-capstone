import {MDBCard, MDBCardBody} from "mdb-react-ui-kit";
import 'mdb-react-ui-kit/dist/css/mdb.min.css';
import "@fortawesome/fontawesome-free/css/all.min.css";
import {Commentary} from "../model/Commentary";
import "./CommentaryCard.css"
import {AppUser} from "../model/AppUser";
import {Route} from "../model/Route";

type CommentCardProps = {
    comment:Commentary;
    deleteACommentaryOfRoute:(routeId:string, commentaryId:string)=>void;
    me:AppUser | undefined
    route:Route;
}

export default function CommentaryCard(props:CommentCardProps){

    function handleOnClick() {
        props.deleteACommentaryOfRoute(props.route.id!, props.comment.id)
    }

    return (
        <div className={"comment-box"}>
            {
                props.comment.routeId === props.route.id &&
                <MDBCard className="mb-4-1" style={{height:100}}>
                    <MDBCardBody>
                        {
                            props.me?.roles[0]==="ADMIN" || props.me?.username === props.comment.postedBy.username ?
                                <>
                                    <div className={"span-btn-trash-comment"}>
                                        <button id={"btn-trash-comment"} onClick={handleOnClick}><i className="bi bi-trash"></i></button>
                                    </div>
                                    <p className="comment">{props.comment.message}</p>
                                </>
                                :
                                <>
                                    <div className={"span-btn-trash-comment"}>
                                    </div>
                                    <p className="comment">{props.comment.message}</p>
                                </>
                        }

                        <div className="d-flex justify-content-between">
                            <div className="d-flex flex-row align-items-center">
                                <p className="comment-user">{props.comment.postedBy.username}</p>
                            </div>
                        </div>
                    </MDBCardBody>
                </MDBCard>
            }

        </div>

    )
}