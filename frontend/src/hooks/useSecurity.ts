import {useState} from "react";
import axios from "axios";
import {toast} from "react-toastify";

export default function useSecurity(){

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
            .catch((error)=>toast.error(error.message+ ": Username or password is wrong!"))
    }

    return {me, handleLogin, username, setUsername, password, setPassword}
}