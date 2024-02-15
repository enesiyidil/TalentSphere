import React from 'react';
// import { Routes, Route, BrowserRouter as Router, } from 'react-router-dom';
// import '../src/Css/navbar/navbar.css';
// import RegisterPage from './components/register';
// import NavigationBar from './components/NavigationBar';
// import UserLoginForm from './components/login';
// import AdminLoginForm from './components/adminlogin';
// import ManagerandVisitor from './components/managerandvisitor';


// function App() {
//   return (
//     <Router>
        
//             <NavigationBar/>
//               <Routes>
//               <Route path="/"></Route>
//               <Route path="/admin" element={<AdminLoginForm />} />
//               <Route path="/login" element={<UserLoginForm/>}></Route>
//               <Route path="/register" element={<RegisterPage />}></Route>
//               <Route path="/managerpage" element={<ManagerandVisitor/>}></Route>
//               </Routes>
          
              
   
//     </Router>
    
//   );
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
