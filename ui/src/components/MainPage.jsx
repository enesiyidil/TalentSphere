import {UserProfileContextProvider} from "../context/UserProfileContext.jsx";
import {UserProfile} from "./UserProfile.jsx";
import React from "react";
import {useSelector} from "react-redux";
import '../Css/mainpage.css'
import ManagerPage from "./ManagerPage.jsx";
import {ManagerContextProvider} from "../context/ManagerContext.jsx";
import {ManageRequestContextProvider} from "../context/ManageRequestContext.jsx";
import {Personal} from "./Personal.jsx";
import {Route} from "react-router-dom";

export default function MainPage({page}) {
    const role = useSelector((state) => state.role);
    const userProfile = useSelector((state) => state.userProfile);
    return (<>
        <main><div className="left-area">
            {role && <UserProfileContextProvider>
                <UserProfile></UserProfile>
            </UserProfileContextProvider>}
        </div>

            {(Object.keys(userProfile).includes("companies") && userProfile.id) &&
            <div className="right-area">
                {role === "ADMIN" && <div>admin page</div>}
                {role === "MANAGER" && (page ? (page === 'personal' && <Personal />): <ManagerPage />)}
                {role === "PERSONAL" && <div>personal page</div>}
                {role === "VISITOR" && <div>admin page</div>}
            </div>}
        </main>
    </>)
}