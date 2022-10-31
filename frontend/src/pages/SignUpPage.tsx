import React from "react";
import "./SignInPage.css"
import Dropdown from "react-bootstrap/Dropdown";
import axios from "axios";
import "./SignUpPage.css"
import SignUp from "../components/SignUp";
import {AppUser} from "../model/AppUser";
import {toast} from "react-toastify";


export default function SignUpPage(){



    function register(newUser:AppUser){
        axios.post("api/user/register", {username:newUser.username, password:newUser.password})
            .then((response)=>{return response.data})
            .then(()=>toast.success("Welcome! Registration succeed!"))
    }

    return(
        <div className={"mainpage"}>
            <div className={"dropdown"}>
                <Dropdown>
                    <Dropdown.Toggle className={"btn-primary-main"} variant="primary" id="login-mainpage">
                        <i className="fa fa-bars"></i>
                    </Dropdown.Toggle>

                    <Dropdown.Menu>
                        <Dropdown.Item href="#/sign-in">Sign in</Dropdown.Item>
                        <Dropdown.Item href="#/sign-up">Sign up</Dropdown.Item>
                    </Dropdown.Menu>
                </Dropdown>
            </div>
            <section className={"sec-title"}>
                <h2 id={"title"}>Running Route Advisor</h2>
            </section>
            <section className={"sec-login"}>
                    <SignUp register={register}/>
            </section>
        </div>
    )
}
