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
import {Calendar, momentLocalizer} from "react-big-calendar";
import moment from 'moment';

const localizer = momentLocalizer(moment);

// Tatil günlerinin bir örneği
const holidays = [
    { title: 'Kafa Tatili', start: new Date(2024, 1, 15), end: new Date(2024, 3, 1) },
    { title: 'Bayram Tatili', start: new Date(2024, 3, 10), end: new Date(2024, 3, 13) }
    // Diğer tatil günlerini buraya ekleyin
];

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
                {role === "MANAGER" && (page ? (<>
                    {page === 'personal' && <Personal />}
                    {page === 'calendar' &&  <Calendar
                        localizer={localizer}
                        events={holidays}
                        startAccessor="start"
                        endAccessor="end"
                        style={{margin: '50px',height: "500px", overflow: "auto",scale: 0.1}}
                    />}
                </>): <ManagerPage />)}
                {role === "PERSONAL" && <div>personal page</div>}
                {role === "VISITOR" && <div>admin page</div>}
            </div>}
        </main>
    </>)
}