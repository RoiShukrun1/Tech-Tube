import React from 'react';
import loginIcon from '../../../../../db/icons/login-icon.svg';
import './loginButton.css'
import { Link } from 'react-router-dom';

function LoginButton() {
  return (
    <Link to="/login">
      <button className="login-button">
        <img className='login-button-img' src={loginIcon} alt="login" />
        <span className="tooltip-text">Login/ Sign Up</span>
      </button>
    </Link>
  );
}

export default LoginButton;
