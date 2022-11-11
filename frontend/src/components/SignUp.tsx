import PasswordChecklist from "react-password-checklist";
import {Link} from "react-router-dom";
import React, {ChangeEvent, useState} from "react";
import {AppUser} from "../model/AppUser";

type SignUpProps = {
    register:(appUser:AppUser) =>void

}

export default function SignUp (props:SignUpProps){

    const [passwordAgain, setPasswordAgain] = useState("")

    const emptyAppUserPlaceholder: AppUser = {
        username:"",
        password: ""
    }
    const [user, setUser] = useState(emptyAppUserPlaceholder);

    const handleChange=(event:ChangeEvent<HTMLInputElement>)=> {
        const inputFieldValue = event.target.value;
        const inputFieldName = event.target.name;
        setUser( oldUser =>(
                { ...oldUser,
                    [inputFieldName]:inputFieldValue
                }
            )
        )
    }

    function handleRegister() {
        props.register(user)
    }

    return(
        <div className={"form-login"}>
            <form>
                <div className="form-group">
                    <label>User name</label>
                    <input type="email" className="form-control" id="exampleInputEmail1"
                           aria-describedby="emailHelp" placeholder="Enter User name"
                           name={"username"}
                           value={user.username}
                           onChange={handleChange}/>
                </div>
                <form className="form-group">
                    <label htmlFor="exampleInputPassword1">Password</label>
                    <input type="password" className="form-control"
                           placeholder="Password"
                           name={"password"}
                           value={user.password}
                           onChange={handleChange}/>
                    <label htmlFor="exampleInputPassword1">Password Again</label>
                    <input type="password" className="form-control"
                           placeholder="Password Again"
                           onChange={(event)=>setPasswordAgain(event.target.value)}/>
                </form>
                <div className={"password-check"}>
                    <PasswordChecklist
                        rules={["minLength","specialChar","number","capital","match"]}
                        minLength={8}
                        value={user.password}
                        valueAgain={passwordAgain}
                        onChange={(isValid) => {}}
                    />
                </div>
                <Link to={"/sign-in"}>
                    <button type="submit" className="btn btn-primary" id={"btn-sign"} onClick={handleRegister}>Register</button>
                </Link>

            </form>

        </div>
    )
}