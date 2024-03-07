import styles from "../Css/MainPages.module.css";
import NavigationBar from "../components/NavigationBar.jsx";
import {BrowserRouter, Route, Routes, Navigate} from "react-router-dom";
import ShowRoomPage from "./ShowRoomPage.jsx";
import RegisterPage from "./RegisterPage.jsx";
import LoginPage from "./LoginPage.jsx";
import HomePage from "./HomePage.jsx";
import {RegisterContextProvider} from "../context/RegisterContext.jsx";
import {LoginContextProvider} from "../context/LoginContext.jsx";

const MainPage = () => {

    return (<>
        <div  className={styles["background-wrapper"]}>
        </div>
        <div className={styles["main-wrapper"]}>
            <BrowserRouter>
                <header className={styles["header-wrapper"]}>
                    <LoginContextProvider><NavigationBar/></LoginContextProvider>
                </header>
                <main className={styles["main-wrapper"]}>
                    <Routes>
                        <Route index element={<ShowRoomPage/>}/>
                        <Route path="/register"
                               element={<RegisterContextProvider><RegisterPage/></RegisterContextProvider>}/>
                        <Route path="/login" element={<LoginContextProvider><LoginPage/></LoginContextProvider>}/>
                        <Route path="/admin" element={<LoginContextProvider><LoginPage/></LoginContextProvider>}/>
                        <Route path="/home" element={<HomePage page="home"/>}/>
                        <Route path="/home/addCompany" element={<HomePage page="addCompany"/>}/>
                        <Route path="/home/addManager" element={<HomePage page="addManager"/>}/>
                        <Route path="/home/approveComment" element={<HomePage page="approveComment"/>}/>
                        <Route path="/home/calendar" element={<HomePage page="calendar"/>}/>
                        <Route path="/home/approveHoliday" element={<HomePage page="approveHoliday"/>}/>
                        <Route path="/home/payment" element={<HomePage page="payment"/>}/>
                        <Route path="/home/approvePayment" element={<HomePage page="approvePayment"/>}/>
                        <Route path="/home/comment" element={<HomePage page="comment"/>}/>
                        <Route path="/home/personals" element={<HomePage page="personals"/>}/>
                        <Route exact path="*" element={<HomePage page="home"/>}/>
                    </Routes>
                </main>
            </BrowserRouter>
        </div>

    </>)
}

export default MainPage;