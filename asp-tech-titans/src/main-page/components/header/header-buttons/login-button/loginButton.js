import React, { useContext } from 'react';
import loginIcon from '../../../../../db/icons/login-circle-icon.svg';
import loginIconDarkMode from '../../../../../db/icons/login-icon-dm.svg';
import './loginButton.css';
import { Link } from 'react-router-dom';
import { ThemeContext } from '../../../../../contexts/themeContext';
import { LoginContext } from '../../../../../contexts/loginContext';

function LoginButton() {
  const { darkMode } = useContext(ThemeContext);
  const { login, loggedOut } = useContext(LoginContext);

  const handleButtonClick = () => {
    if (login) {
      loggedOut();
    }
  };

  return (
    <Link to={login ? "/" : "/login"}>
      <button className="login-button" onClick={handleButtonClick}>
        <img
          className='login-button-img'
          src={darkMode ? (login ? login.image : loginIconDarkMode) : (login ? login.image : loginIcon)}
          alt={login ? "logout" : "login"}
        />
        <span className="tooltip-text">{login ? "Log out" : "Login/ Sign Up"}</span>
      </button>
    </Link>
  );
}

export default LoginButton;
