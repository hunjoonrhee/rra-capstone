import {Link} from "react-router-dom";
import React, {useState} from "react";
import axios from "axios";
import {toast} from "react-toastify";

export default function SignIn(){


    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [me, setMe] = useState("")


    function handleLogin() {
        axios.get("api/user/login", {auth: {username, password}})
            .then((response)=>{return response.data})
            .then((data)=>setMe(data))
            .then(()=>toast.success("Logged in!"))
            .then(()=>setUsername(""))
            .then(()=>setPassword(""))
            .catch((error)=>toast.error("Username or password is wrong!"))
    }

    function handleLogout() {
        axios.get("api/user/logout")
            .then(()=>setMe(""))
    }

    return(
        <>
            {
                !me ?
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
                    :
                    <>
                        <p>Logged in as: {me}</p>
                        <Link to={"/"}>
                            <button>go to main</button>
                        </Link>
                        <button onClick={handleLogout}>Logout</button>
                    </>
            }

        </>



    )
}