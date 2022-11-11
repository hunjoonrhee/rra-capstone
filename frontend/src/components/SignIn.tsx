import {Link} from "react-router-dom";
import React from "react";


type SignInProps = {
    me:string
    handleLogin: ()=> void
    setUserName: (user:string)=> void
    setUserPassword: (password:string)=>void
}


export default function SignIn(props:SignInProps){


    return(
        <>
            {
                !props.me &&
                    <div className={"form-login"}>
                        <form>
                            <div className="form-group">
                                <label >User name</label>
                                <input type="email" className="form-control" id="exampleInputEmail1"
                                       aria-describedby="emailHelp" placeholder="Enter User name"
                                       onChange={event => props.setUserName(event.target.value)}/>
                            </div>
                            <div className="form-group">
                                <label htmlFor="exampleInputPassword1">Password</label>
                                <input type="password" className="form-control" id="exampleInputPassword1"
                                       placeholder="Password"
                                       onChange={event=> props.setUserPassword(event.target.value)}/>
                            </div>
                            <Link to={"/"}>
                                <button type="submit" className="btn btn-primary" id={"btn-sign"} onClick={props.handleLogin}>Submit</button>
                            </Link>

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