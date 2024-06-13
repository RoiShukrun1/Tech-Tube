import React from 'react';
import loginIcon from '../../../../../db/icons/login-icon.svg';
import './loginButton.css'

function LoginButton() {
  return (
    <button className="login-button">
      <img className='login-button-img' src={loginIcon} alt="login" />
      <span className="tooltip-text">Login/ Sign Up</span>
    </button>
  );
}

export default LoginButton;
