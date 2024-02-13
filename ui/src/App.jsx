import React from 'react';
import {BrowserRouter as Router, Route, Routes,} from 'react-router-dom';
import '../src/Css/home/home.css';
import '../src/Css/register/register.css';
import RegisterPage from './components/register';
import NavigationBar from './components/NavigationBar';
import UserLoginForm from './components/login';
import {UserProfileContextProvider} from "./context/UserProfileContext.jsx";
import {UserProfile} from "./components/UserProfile.jsx"

function App() {
    return (
        <>
            <Router>
                <header className="body">
                    <div>
                        <NavigationBar/>
                        <Routes>
                            <Route path="/"></Route>
                            <Route path="/login" element={<UserLoginForm/>}></Route>
                            <Route path="/register" element={<RegisterPage/>}></Route>
                        </Routes>
                    </div>
                </header>
            </Router>
            <main>
                <UserProfileContextProvider>
                    <UserProfile></UserProfile>
                </UserProfileContextProvider>
            </main>
        </>
    );
}

export default App;
