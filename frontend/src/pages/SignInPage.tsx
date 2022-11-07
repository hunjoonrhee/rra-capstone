import React from "react";
import "./SignInPage.css"
import Dropdown from "react-bootstrap/Dropdown";

import SignIn from "../components/SignIn";
import 'react-toastify/dist/ReactToastify.css';
import useSecurity from "../hooks/useSecurity";


export default function SignInPage(){
    const {me, handleLogin, setUsername} = useSecurity();

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
                <SignIn me={me} handleLogin={handleLogin} setUsername={()=>setUsername}/>
            </section>
        </div>
    )
}
