import React, { useState } from 'react';
import { useForm } from 'react-hook-form';
import { useNavigate } from 'react-router-dom';
import * as data from './user.json';
import '../Css/login/login.css';
import NavigationBar from '../components/NavigationBar';

export default function UserLoginForm() {
  const {
    register,
    formState: { errors, isSubmitting },
  } = useForm();

  const navigate = useNavigate();
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  const handleLogout = () => {
    setIsLoggedIn(false);
  };

  const handleLoginSubmit = () => {
    const userRole = data.user.role;

    switch (userRole) {
      case 'manager':
        navigate('/managerpage', { replace: true });
        setIsLoggedIn(true);
        break;

      case 'visitor':
        navigate('/visitorpage', { replace: true });
        setIsLoggedIn(true);
        break;
    }
  };

  return (
    <div>
      <NavigationBar isLoggedIn={isLoggedIn} handleLogout={handleLogout} />
        
      {isLoggedIn ? (
        <button className="logout-button" onClick={handleLogout}>
          Logout
        </button>
      ) : (
        <form className="form UserLoginForm">
          <input
            {...register('email', {
              required: 'Email giriniz',
            })}
            type="email"
            placeholder="Email"
            className="form-group input"
          />
          {errors.email && (
            <p className="text-red-500">{`${errors.email.message}`}</p>
          )}

          <input
            {...register('password', {
              required: 'Şifre giriniz',
              minLength: {
                value: 10,
                message: 'Password must be at least 10 characters',
              },
            })}
            type="password"
            placeholder="Şifre"
            className="form-group input"
          />
          {errors.password && (
            <p className="text-red-500">{`${errors.password.message}`}</p>
          )}
          <button
            disabled={isSubmitting}
            type="button"
            className="form-group button"
            onClick={handleLoginSubmit}
          >
            Submit
          </button>
        </form>
      )}
    </div>
  );
}
