import React from "react";
import "./SignInPage.css"
import Dropdown from "react-bootstrap/Dropdown";

import SignIn from "../components/SignIn";
import 'react-toastify/dist/ReactToastify.css';
import useSecurity from "../hooks/useSecurity";
import SignIODropDownMenu from "../components/SignIODropDownMenu";

type SignInPageProps = {
    me:string,
    handleLogin: ()=>void,
    setUserName:(user:string)=>void,
    setUserPassword:(password:string) => void,
    handleLogout:()=>void
}

export default function SignInPage(props:SignInPageProps){

    return(
        <div className={"mainpage"}>
            <SignIODropDownMenu me={props.me} handleLogout={props.handleLogout}/>
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
