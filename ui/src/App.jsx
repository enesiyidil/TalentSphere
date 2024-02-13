import React from 'react';
import {BrowserRouter as Router, Route, Routes,} from 'react-router-dom';
import '../src/Css/home/home.css';
import '../src/Css/register/register.css';
import RegisterPage from './components/register';
import NavigationBar from './components/NavigationBar';
import UserLoginForm from './components/login';
import {UserProfileContextProvider} from "./context/UserProfileContext.jsx";
import {UserProfile} from "./components/UserProfile.jsx"
import {useSelector} from "react-redux";
import {LoginContextProvider} from "./context/LoginContext.jsx";
import {RegisterContextProvider} from "./context/RegisterContext.jsx";

function App() {
    const role = useSelector((state) => state.role);
    return (
        <>

                <Router>
                    <header className="body">
                        <div>
                            <NavigationBar/>
                            <Routes>
                                <Route path="/"></Route>
                                <Route path="/login"
                                       element={<LoginContextProvider><UserLoginForm/></LoginContextProvider>}></Route>
                                <Route path="/register" element={<RegisterContextProvider><RegisterPage/></RegisterContextProvider>}></Route>
                            </Routes>
                        </div>
                    </header>
                </Router>

            <main>{role &&
                <UserProfileContextProvider>
                    <UserProfile></UserProfile>
                </UserProfileContextProvider>}
            </main>
        </>
    );
}

export default App;
