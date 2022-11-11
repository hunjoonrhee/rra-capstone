import Dropdown from "react-bootstrap/Dropdown";
import React from "react";
import "./DropDownMenu.css"

type DropDownMenuProps = {
    me:string
    handleLogout:()=>void
}

export default function DropDownMenu(props:DropDownMenuProps){

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
                            <Dropdown.Item href="#/sign-in">Signed in as {props.me}</Dropdown.Item>
                            <Dropdown.Item href="#/" onClick={props.handleLogout}>Sign out</Dropdown.Item>
                            <Dropdown.Item><i className="bi bi-link"></i> Share this page!</Dropdown.Item>
                        </Dropdown.Menu>
                    </Dropdown>

            }

        </div>
    )
}