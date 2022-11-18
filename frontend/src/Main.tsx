/* eslint-disable react-hooks/exhaustive-deps */
import useMyRoutes from "./hooks/useMyRoutes";
import useSecurity from "./hooks/useSecurity";
import React, {useEffect} from "react";
import {Route, Routes} from "react-router-dom";
import MainPage from "./pages/MainPage";
import SignInPage from "./pages/SignInPage";
import SignUpPage from "./pages/SignUpPage";
import RoutesPage from "./pages/RoutesPage";
import RouteDetailsPage from "./pages/RouteDetailsPage";
import './App.css';


export default function Main() {
    const {location, setLocation, getAllFoundRoutes,getAllRoutes,getAllPhotos, photos,
        setFilterTag, getCurrentLocation, currentAddress, routes,deleteAPhotoOfRoute, getAllCommentariesOfRoute,
        comments, addANewCommentary,deleteACommentaryOfRoute,
        filterTag, allFilter, deleteARoute, setAllFilter, foundRoutes} = useMyRoutes()
    const {me, handleLogin, setUserName, setUserPassword, register, handleLogout} = useSecurity()

    useEffect(()=>{
        getAllRoutes();
    }, [])

    useEffect(()=>{
        getAllPhotos();
    }, [])
    useEffect(()=>{
        getAllCommentariesOfRoute();
    }, [])
    useEffect(()=>{
        getAllFoundRoutes()
    }, [])

    return(
        <>
            <Routes>
                <Route path = {"/"} element = {<MainPage me={me}
                                                         getAllFoundRoutes={getAllFoundRoutes}
                                                         handleLogout={handleLogout}
                                                         setLocation={setLocation}
                                                         location={location}/>}/>
                <Route path = {"/sign-in"} element = {<SignInPage me={me} handleLogin={handleLogin} handleLogout={handleLogout}
                                                                  setUserName={setUserName} setUserPassword={setUserPassword}/>}/>
                <Route path = {"/sign-up"} element = {<SignUpPage me={me} register={register} handleLogout={handleLogout}/>}/>
                <Route path = {`/routes/:id`} element = {<RoutesPage me={me}
                                                                     handleLogout={handleLogout}
                                                                     setFilterTag={setFilterTag}
                                                                     setAllFilter={setAllFilter}
                                                                     getCurrentLocation={getCurrentLocation}
                                                                     currentAddress={currentAddress}
                                                                     getAllFoundRoutes={getAllFoundRoutes}
                                                                     filterTag={filterTag} foundRoutes={foundRoutes}
                                                                     deleteARoute={deleteARoute}
                                                                     allFilter={allFilter}
                                                                     setLocation={setLocation}
                                                                     location={location}
                />}/>
                <Route path = {"/route/:id/details"} element = {<RouteDetailsPage me={me}
                                                                                  routes={routes}
                                                                                  handleLogout={handleLogout}
                                                                                  location={location}
                                                                                  deleteAPhotoOfRoute={deleteAPhotoOfRoute}
                                                                                  getAllFoundRoutes={getAllFoundRoutes}
                                                                                  getAllPhotos={getAllPhotos}
                                                                                  photos={photos}
                                                                                  addANewCommentary={addANewCommentary}
                                                                                  comments={comments}
                                                                                  deleteACommentaryOfRoute={deleteACommentaryOfRoute}/>}/>
            </Routes>

        </>

    )
}