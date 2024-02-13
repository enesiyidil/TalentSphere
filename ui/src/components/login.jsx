import React, {useContext} from 'react';
import { useForm } from 'react-hook-form';
import { useNavigate } from 'react-router-dom';
import * as data from './user.json'
import '../Css/login.css'
import {useSelector} from "react-redux";
import {LoginContext} from "../context/LoginContext.jsx";


export default function UserLoginForm() {
    const role = useSelector((state) => state.role);
    const {doLogin} = useContext(LoginContext);
  const {
    register,
    formState: { errors, isSubmitting },
      getValues
  } = useForm();

  const navigate = useNavigate();
  

  const handleLoginSubmit = () => {

      doLogin(getValues('email'), getValues('password'))
      switch(role) {
        case('MANAGER'):
          navigate('/managerpage', {replace: true});
          break;
          
        case('VISITOR'):
          navigate('/visitorpage', {replace: true});
          break;
        case('PERSONAL'):
          navigate('/personalpage', {replace: true});
          break;

      }
  }


  return (
    <form className="form-container UserLoginForm">
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
  );
}
