import Dropdown from "react-bootstrap/Dropdown";
import React from "react";
import "./DropDownMenu.css"
import CopyToClipboard from "react-copy-to-clipboard";
import {toast} from "react-toastify";
import {AppUser} from "../model/AppUser";

type DropDownMenuProps = {
    me:AppUser | undefined
    handleLogout:()=>void
}

export default function DropDownMenu(props:DropDownMenuProps){


    function handleOnCopy() {
            toast.success('🍀This url is copied! Share this route!', {
                position: "top-right",
                autoClose: 2000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
                theme: "light",
            });
    }

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
                            <Dropdown.Item href="#/sign-in">Signed in as {props.me.username}</Dropdown.Item>
                            <Dropdown.Item href="#/" onClick={props.handleLogout}>Sign out</Dropdown.Item>

                            <CopyToClipboard text={window.location.href} onCopy={handleOnCopy}>
                                <Dropdown.Item><i className="bi bi-link"></i> Share this page!</Dropdown.Item>
                            </CopyToClipboard>


                        </Dropdown.Menu>
                    </Dropdown>

            }

        </div>
    )
}