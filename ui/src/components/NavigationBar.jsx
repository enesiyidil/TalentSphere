import React from 'react';

import PropTypes from 'prop-types';
import { Link } from 'react-router-dom';
import '../Css/navbar/navbar.css';

const NavigationBar = ({ handleLogout }) => {
  return (
    <nav className="navbar">
      <div className="logo">
        <Link to="/">Talent Sphere</Link>
      </div>
      <ul className="nav-links">
        <li>
          <Link to="/register">Register</Link>
        </li>
        <li>
          <Link to="/login">Login</Link>
        </li>
        <li>
          <Link to="/admin">Admin</Link>
        </li>
      </ul>
    </nav>
  );
};

NavigationBar.propTypes = {
  handleLogout: PropTypes.func.isRequired,
};

export default NavigationBar;

