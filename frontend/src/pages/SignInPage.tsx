import React from "react";
import "./SignInPage.css"
import SignIn from "../components/SignIn";
import 'react-toastify/dist/ReactToastify.css';
import SignIODropDownMenu from "../components/SignIODropDownMenu";
import {useNavigate} from "react-router-dom";

type SignInPageProps = {
    me:string,
    handleLogin: ()=>void,
    setUserName:(user:string)=>void,
    setUserPassword:(password:string) => void,
    handleLogout:()=>void
}

export default function SignInPage(props:SignInPageProps){

    const navigate = useNavigate();

    return(
        <div className={"mainpage"}>
            <div className={"div-dropdown-signIn"}>
                <button className="btn btn-outline-secondary-rd"
                        onClick={()=>navigate(-1)}><i className="bi bi-caret-left-fill"></i> Back</button>
                <SignIODropDownMenu me={props.me} handleLogout={props.handleLogout}/>
            </div>

            <section className={"sec-title"}>
                <h2 id={"title"}>Running Route Advisor</h2>
            </section>
            <section className={"sec-login"}>
                <SignIn me={props.me} handleLogin={props.handleLogin} setUserName={props.setUserName}
                 setUserPassword={props.setUserPassword}/>
            </section>
        </div>
    )
}
