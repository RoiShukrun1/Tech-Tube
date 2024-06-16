import React, { useContext } from 'react';
import loginIcon from '../../../../../db/icons/login-circle-icon.svg';
import loginIconDarkMode from '../../../../../db/icons/login-icon-dm.svg';
import './loginButton.css';
import { Link } from 'react-router-dom';
import { ThemeContext } from '../../../../../contexts/themeContext';

function LoginButton() {
  const { darkMode } = useContext(ThemeContext);

  return (
    <Link to="/login">
      <button className="login-button">
        <img
          className='login-button-img'
          src={darkMode ? loginIconDarkMode : loginIcon}
          alt="login"
        />
        <span className="tooltip-text">Login/ Sign Up</span>
      </button>
    </Link>
  );
}

export default LoginButton;
