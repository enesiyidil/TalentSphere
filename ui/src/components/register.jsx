import React, { useState } from 'react';
import { useForm } from 'react-hook-form';
import '../Css/register/register.css';
import 'react-datepicker/dist/react-datepicker.css';

export default function RegisterPage() {
  const roleFields = {
    manager: ['name', 'surname', 'email', 'password', 'confirmPassword', 'phone', 'birthday'],
    visitor: ['name', 'surname', 'email'],
  };

  const [selectedRole, setSelectedRole] = useState('');
  const [fieldsToShow, setFieldsToShow] = useState([]);

  const {
    register,
    handleSubmit,
    reset, // useForm hook'unun bir parçası olarak reset fonksiyonunu alıyoruz
    formState: { errors, isSubmitting },
  } = useForm();

  const handleClearClick = () => {
    reset(); // Formu sıfırlayacak olan reset fonksiyonunu çağırıyoruz
  };

  const handleRoleChange = (event) => {
    const role = event.target.value;
    setSelectedRole(role);
    setFieldsToShow(roleFields[role] || []);
  };

  return (
    <div className="container">
      <form onSubmit={handleSubmit} className="form">
        <div>
          <label htmlFor="role">Rol:</label>
          <select
            {...register('role', {
              required: 'Rol seçiniz',
            })}
            className="textarea"
            onChange={handleRoleChange}
          >
            <option value="">Seçiniz</option>
            <option value="manager">Manager</option>
            <option value="visitor">Visitor</option>
          </select>
        </div>
        {errors.role && (
          <p className="text-red-500">{`${errors.role.message}`}</p>
        )}

        {fieldsToShow.map((fieldName) => (
          <div key={fieldName}>
            <input
              {...register(fieldName, {
                required: `${fieldName} alanını doldurunuz`,
              })}
              type={fieldName === 'password' || fieldName === 'confirmPassword' ? 'password' : 'text'}
              placeholder={fieldName}
              className="textarea"
            />
            {errors[fieldName] && (
              <p className="text-red-500">{`${errors[fieldName].message}`}</p>
            )}
          </div>
        ))}

        {selectedRole && (
          <div>
            <div>
              <label htmlFor="birthday"></label>
              {errors.birthday && <p>{errors.birthday.message}</p>}
            </div>
          </div>
        )}

<button disabled={isSubmitting} type="submit" className="button">
          Submit
        </button>
        <button type='button' onClick={handleClearClick} className="button">Clear</button>
      </form>
    </div>
  );
}

