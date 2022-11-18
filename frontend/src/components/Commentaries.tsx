import 'mdb-react-ui-kit/dist/css/mdb.min.css';
import "@fortawesome/fontawesome-free/css/all.min.css";
import {
    MDBCard,
    MDBCardBody,
    MDBCol,
    MDBContainer,
    MDBInput,
    MDBRow,
    MDBBtn
} from "mdb-react-ui-kit";
import "./Commentaries.css"
import CommentaryCard from "./CommentaryCard";
import {useState} from "react";
import {Commentary} from "../model/Commentary";
import {Route} from "../model/Route";
import {AppUser} from "../model/AppUser";
import {toast} from "react-toastify";

type CommentariesProps = {
    addANewCommentary:(routeId: string, user:string, newCommentary:Commentary)=>void;
    route:Route;
    me:AppUser | undefined
    comments:Commentary[];
    deleteACommentaryOfRoute:(routeId:string, commentaryId:string)=>void;
}

export default function Commentaries(props:CommentariesProps){

    const [comment, setComment] = useState("");

    function postANewComment (){
        const newCommentary:Commentary = {
            id:"",
            message:comment,
            routeId:props.route.id,
            postedBy:{
                username:"",
                password:"",
                roles:[]
            },
            timeStamp:""
        }
        if(props.me!==undefined){
            props.addANewCommentary(props.route.id!, props.me?.username, newCommentary)
        }else{
            toast('🦄 You have to log in for posting comment!', {
                position: "top-center",
                autoClose: 5000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
                theme: "light",
            });
        }
        setComment("");

    }

    return (
        <MDBContainer className="mt-5-c">
            <MDBRow className="justify-content-center">
                <MDBCol md="8" lg="6" className={"col-md-8"}>
                    <MDBCard className="border-c">
                        <MDBCardBody style={{maxHeight:180}}>
                            <div className={"input-btn"}>
                                <MDBInput className={"input-c"} id={"mb-4-input"} placeholder="Type comment..."
                                          label="+ Add a comment" value={comment}
                                onChange={(event)=>setComment(event.target.value)}/>
                                <MDBBtn className={"comment-post-btn"} type={"button"}
                                onClick={postANewComment}>Post</MDBBtn>
                            </div>
                            {
                                props.comments.map((comment)=>{
                                    return <CommentaryCard key={comment.id} me={props.me}
                                                           route={props.route}
                                                           comment={comment}
                                                           deleteACommentaryOfRoute={props.deleteACommentaryOfRoute}/>
                                })
                            }

                        </MDBCardBody>
                    </MDBCard>
                </MDBCol>
            </MDBRow>
        </MDBContainer>
    );
}