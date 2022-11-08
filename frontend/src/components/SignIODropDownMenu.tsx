import Dropdown from "react-bootstrap/Dropdown";
import React, {useState} from "react";
import {Link} from "react-router-dom";
import "./SignIODropDownMenu.css"

type SignIODropDownMenuProps = {
    me:string
    handleLogout:()=>void
}

export default function SignIODropDownMenu(props:SignIODropDownMenuProps){

    return (
        <div className={"dropdown"}>
            {
                !props.me ?
                    <Dropdown>
                        <Dropdown.Toggle className={"btn-primary"} variant="primary">
                            <i className="fa fa-bars"></i>
                        </Dropdown.Toggle>

                        <Dropdown.Menu>
                            <Dropdown.Item href="#/sign-in">Sign in</Dropdown.Item>
                            <Dropdown.Item href="#/sign-up">Sign up</Dropdown.Item>
                        </Dropdown.Menu>
                    </Dropdown>
                :
                    <Dropdown>
                        <Dropdown.Toggle className={"btn-primary"} variant="primary">
                            <i className="fa fa-bars"></i>
                        </Dropdown.Toggle>

                        <Dropdown.Menu>
                            <Dropdown.Item href="#/sign-in">Signed in as {props.me.split("@")[0]}</Dropdown.Item>
                            <Dropdown.Item href="#/" onClick={props.handleLogout}>Sign out</Dropdown.Item>
                        </Dropdown.Menu>
                    </Dropdown>

            }

        </div>
    )
}