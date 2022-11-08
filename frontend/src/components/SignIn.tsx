import {Link} from "react-router-dom";
import React from "react";

import useSecurity from "../hooks/useSecurity";


type SignInProps = {
    me:string
    handleLogin: ()=> void
    setUsername: ()=> void
}


export default function SignIn(props:SignInProps){


    const {me, handleLogin, setUsername, setPassword} = useSecurity()

    return(
        <>
            {
                !me &&
                    <div className={"form-login"}>
                        <form onSubmit={handleLogin}>
                            <div className="form-group">
                                <label htmlFor="exampleInputEmail1">Email address</label>
                                <input type="email" className="form-control" id="exampleInputEmail1"
                                       aria-describedby="emailHelp" placeholder="Enter email"
                                       onChange={event => setUsername(event.target.value)}/>
                                <small id="emailHelp" className="form-text text-muted">We'll never share
                                    your email with anyone else.</small>
                            </div>
                            <div className="form-group">
                                <label htmlFor="exampleInputPassword1">Password</label>
                                <input type="password" className="form-control" id="exampleInputPassword1"
                                       placeholder="Password"
                                       onChange={event=> setPassword(event.target.value)}/>
                            </div>
                            <button type="submit" className="btn btn-primary" id={"btn-sign"}>Submit</button>
                        </form>
                        <Link to={"/sign-up"}>
                            <button className="btn btn-primary" id={"btn-sign"}>
                                Register
                            </button>
                        </Link>

                    </div>

            }

        </>



    )
}