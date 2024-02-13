import React, {useContext} from 'react';
import { useForm } from 'react-hook-form';
//import { useHistory } from 'react-router-dom';
import DatePicker from 'react-datepicker';
import '../Css/register/register.css'
import 'react-datepicker/dist/react-datepicker.css';
import {RegisterContext} from "../context/RegisterContext.jsx";


export default function RegisterPage() {
  //const history=useHistory()
  const {
    register,
    // handleSubmit,
    formState: { errors, isSubmitting },
    getValues,
  } = useForm();

  const {doRegister} = useContext(RegisterContext);

  // const handleRoleSelection = (role) => {
  //   switch (role) {
  //     case 'manager':
  //       history.push('/managerpage');
  //       break;
  //     case 'visitor':
  //       history.push('/visitorpage');
  //       break;
  //     case 'personal':
  //       history.push('/personalpage');
  //       break;
  //     default:
  //       break;
  //   }
  // };berresumelek06@gmail.com

    const handleSubmit = () => {
        const obje = {
            username: getValues("username"),
            name: getValues('name'),
            surname: getValues('surname'),
            title: getValues('surname'),
            phone: getValues('phone'),
            email: getValues('email'),
            password: getValues('password'),
            rePassword: getValues('confirmPassword'),
            role: getValues('role'),
        };
        console.log(obje)
        doRegister(obje)
    }
  
  return (
    <form onSubmit={handleSubmit} className="form RegisterPage">
      <input
        {...register('name', {
          required: 'İsminizi giriniz',
        })}
        type="text"
        placeholder="Name"
        className="textarea"
      />

      <input
        {...register('surname', {
          required: 'Soyisminizi giriniz',
        })}
        type="text"
        placeholder="Last Name"
        className="textarea"
      />
      <input
        {...register('username', {
          required: 'Kullanıcı adınızı giriniz',
        })}
        type="text"
        placeholder="Username"
        className="textarea"
      />

      <input
        {...register('email', {
          required: 'Email giriniz',
        })}
        type="email"
        placeholder="Email"
        className="textarea"
      />
      {errors.email && (
        <p className="text-red-500">{`${errors.email.message}`}</p>
      )}

      <input
        {...register('password', {
          required: 'Şifre giriniz',
          minLength: {
            value: 10,
            message: 'Şifre minimum 10 karakter olmalıdır',
          },
        })}
        type="password"
        placeholder="Şifre"
        className="textarea"
      />
      {errors.password && (
        <p className="text-red-500">{`${errors.password.message}`}</p>
      )}

      <input
        {...register('confirmPassword', {
          required: 'Şifrenizi tekrar giriniz',
          validate: (value) =>
            value === getValues('password') || 'Şifreler aynı değil',
        })}
        type="password"
        placeholder="Tekrar şifre giriniz"
        className="textarea"
      />
      {errors.confirmPassword && (
        <p className="text-red-500">{`${errors.confirmPassword.message}`}</p>
      )}

      <input
        {...register('phone', {
          required: 'Telefon numaranızı giriniz',
        })}
        type="tel"
        placeholder="Phone Number"
        className="textarea"
      />
      {errors.phone && (
        <p className="text-red-500">{`${errors.phone.message}`}</p>
      )}

      <div>
        <label htmlFor="birthday">Doğum Günü:</label>
        <DatePicker
          {...register('birthday', {
            required: 'Lütfen doğum gününüzü seçin',
          })}
          dateFormat="dd/MM/yyyy"
          placeholderText="Doğum Günü Seçin"
          className="px-4 py-2 rounded"
        />
        {errors.birthday && <p>{errors.birthday.message}</p>}
      </div>
      <div className="radio-container">
        <label htmlFor="role">Rol:</label>
        <div>
          <input
            {...register('role', {
              required: 'Rol seçiniz',
            })}
            type="radio"
            id="manager"
            value="MANAGER"
            //onClick={handleRoleSelection('manager')}
          />
          <label htmlFor="manager">Manager</label>

          <input
            {...register('role', {
              required: 'Rol seçiniz',
            })}
            type="radio"
            id="visitor"
            value="VISITOR"
            //onClick={() => handleRoleSelection('visitor')}
          />
          <label htmlFor="visitor">Visitor</label>

          <input
            {...register('role', {
              required: 'Rol seçiniz',
            })}
            type="radio"
            id="personal"
            value="PERSONAL"
           //onClick={() => handleRoleSelection('personal')}
          />
          <label htmlFor="personal">Personal</label>
        </div>
        {errors.role && (
          <p className="text-red-500">{`${errors.role.message}`}</p>
        )}
        </div>
      <button
        disabled={isSubmitting}
        type="button"
        onClick={handleSubmit}
        className="button"
      >
        Submit
      </button>
    </form>
  );
}
