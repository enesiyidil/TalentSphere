import React from 'react';
import styles from './Button.module.css';
// eslint-disable-next-line react/prop-types
export const Button = ({ buttonText, onClick }) => {
  return (
    <button className={styles.button} onClick={onClick}>
      {buttonText}
    </button>
  );
};
