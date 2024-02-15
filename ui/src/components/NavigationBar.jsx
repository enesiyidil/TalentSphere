import React, {useContext} from 'react';

import {Link} from 'react-router-dom';
import '../Css/navbar/navbar.css';
import {useSelector} from "react-redux";
import {LoginContext} from "../context/LoginContext.jsx";

const NavigationBar = () => {
    const role = useSelector((state) => state.role);
    const {handleLogout} = useContext(LoginContext);
    return (
        <nav className="navbar">
            <div className="logo">
                <Link to="/">Talent Sphere</Link>
            </div>
            {role && <div>
                {role === 'MANAGER' && (
                    <ul className="nav-links">
                        <li>
                            <Link to="/mainpage">Home</Link>
                        </li>
                        <li>
                            <Link to="/mainpage">Company</Link>
                        </li>
                        <li>
                            <Link to="/personal">Personal</Link>
                        </li>
                        <li>
                            <Link to="/mainpage">Holiday</Link>
                        </li>
                        <li>
                            <Link to="/mainpage">Payment</Link>
                        </li>
                    </ul>
                )}
            </div>}
            <ul className="nav-links">
                <li>
                    {!role && <Link to="/register">Register</Link>}
                </li>
                <li>
                    {role ?  <button onClick={handleLogout}>Logout</button> : <Link to="/login">Login</Link>}
                </li>
                <li>
                    {!role && <Link to="/admin">Admin</Link>}
                </li>
            </ul>
        </nav>
    );
};

export default NavigationBar;

