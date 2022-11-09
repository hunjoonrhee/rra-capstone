import React from "react";
import "./SignInPage.css"
import "./SignUpPage.css"
import SignUp from "../components/SignUp";
import {AppUser} from "../model/AppUser";
import DropDownMenu from "../components/DropDownMenu";
import {useNavigate} from "react-router-dom";

type SignUpPageProps = {
    me:string
    register:(newUser:AppUser)=>void
    handleLogout:()=>void
}


export default function SignUpPage(props:SignUpPageProps){

    const navigate = useNavigate();

    return(
        <div className={"mainpage"}>
            <div className={"div-dropdown-signUp"}>
                <button className="btn btn-outline-secondary-rd"
                        onClick={()=>navigate(-1)}><i className="bi bi-caret-left-fill"></i> Back</button>
                <DropDownMenu me={props.me} handleLogout={props.handleLogout}/>
            </div>
            <section className={"sec-title"}>
                <h2 id={"title"}>Running Route Advisor</h2>
            </section>
            <section className={"sec-signUp"}>
                    <SignUp register={props.register}/>
            </section>
        </div>
    )
}
