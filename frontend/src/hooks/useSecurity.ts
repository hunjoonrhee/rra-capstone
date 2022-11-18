/* eslint-disable react-hooks/exhaustive-deps */
import {useEffect, useState} from "react";
import axios from "axios";
import {toast} from "react-toastify";
import {AppUser} from "../model/AppUser";
import {useLocation} from "react-router-dom";

export default function useSecurity(){

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [me, setMe] = useState<AppUser | undefined>()
    const [isLoggedOut, setIsLoggedOut] = useState(false);


    function handleLogin() {
        axios.get("api/user/login", {auth: {username, password}})
            .then((response)=>{
                return response.data
            })
            .then((data)=>{
                setMe(data)
            })
            .then(()=>toast.success("Hi!👋 You are logged in!"))
            .then(()=>setUsername(""))
            .then(()=>setPassword(""))
            .catch(()=>toast.error("Username or password is wrong!"))
    }

    function setUserName(user:string){
        setUsername(user);
    }
    function setUserPassword(password:string){
        setPassword(password);
    }

    const location = useLocation()
    useEffect(() => {

        if (location.pathname !== "/")
            handleMe()

    }, [])

    function handleMe(){
        axios.get("api/user/me")
            .then(response => {
                return response.data
            })
            .then((data) => {
                setMe(data)
            })
    }

    function register(newUser:AppUser){
        axios.post("api/user/register", {username:newUser.username, password:newUser.password})
            .then((response)=>{return response.data})
            .then(()=>toast.success("Welcome⭐🏃‍♀️🏃‍♂️️⭐Registration succeed!"))
    }
    function handleLogout() {
        axios.get("api/user/logout")
            .then(()=>setMe(undefined))
            .then(()=>setIsLoggedOut(true))
            .then(()=>toast.success("Good bye 👋 You are logged out."))
    }

    return {me, handleLogin, username, setUserName, password, setUserPassword, register, handleLogout, isLoggedOut}
}