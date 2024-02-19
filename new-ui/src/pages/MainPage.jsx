import styles from "../Css/MainPages.module.css";
import NavigationBar from "../components/NavigationBar.jsx";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import ShowRoomPage from "./ShowRoomPage.jsx";
import RegisterPage from "./RegisterPage.jsx";
import LoginPage from "./LoginPage.jsx";
import HomePage from "./HomePage.jsx";
import {RegisterContextProvider} from "../context/RegisterContext.jsx";
import {LoginContextProvider} from "../context/LoginContext.jsx";

const MainPage = () => {

    return (<>
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
                        <Route path="/home" element={<HomePage/>}/>
                    </Routes>
                </main>
            </BrowserRouter>
        </div>
    </>)
}

export default MainPage;