import React from "react";
import "./SignInPage.css"
import Dropdown from "react-bootstrap/Dropdown";
import axios from "axios";
import "./SignUpPage.css"
import SignUp from "../components/SignUp";
import {AppUser} from "../model/AppUser";
import {toast} from "react-toastify";
import SignIODropDownMenu from "../components/SignIODropDownMenu";

type SignUpPageProps = {
    me:string
    register:(newUser:AppUser)=>void
    handleLogout:()=>void
}


export default function SignUpPage(props:SignUpPageProps){





    return(
        <div className={"mainpage"}>
            <SignIODropDownMenu me={props.me} handleLogout={props.handleLogout}/>
            <section className={"sec-title"}>
                <h2 id={"title"}>Running Route Advisor</h2>
            </section>
            <section className={"sec-login"}>
                    <SignUp register={props.register}/>
            </section>
        </div>
    )
}
