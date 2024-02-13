import React from 'react';
import { Link } from 'react-router-dom';
import '../Css/home/home.css'

const NavigationBar = () => {
  return (
      <header className='navigation-bar'>
<nav>
      <ul>
        <li>
          <Link to="/" >Home</Link>
        </li>
        <li>
          <Link to="/login">Kullanıcı Giriş</Link>
        </li>
        <li>
          <Link to="/register">Kayıt Ol</Link>
        </li>
      </ul>
    </nav>
      </header>
       
    
  );
};
export default NavigationBar;