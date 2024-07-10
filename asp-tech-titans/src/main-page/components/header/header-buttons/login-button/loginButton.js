import React, { useContext, useEffect } from 'react';
import loginIcon from '../../../../../db/icons/login-circle-icon.svg';
import loginIconDarkMode from '../../../../../db/icons/login-icon-dm.svg';
import './loginButton.css';
import { Link } from 'react-router-dom';
import { ThemeContext } from '../../../../../contexts/themeContext';
import { LoginContext } from '../../../../../contexts/loginContext';

/**
 * LoginButton Component
 * 
 * This component renders a button for logging in or out. It uses React Router's Link component 
 * to navigate to the login or home page based on the user's login state. The button icon and 
 * tooltip text change based on the current theme and login state.
 */
function LoginButton() {
  // Retrieve darkMode value from ThemeContext
  const { darkMode } = useContext(ThemeContext);
  // Retrieve login state and logout function from LoginContext
  const { login, loggedOut } = useContext(LoginContext);

  // Construct the image URL based on the login state
  const serverBaseUrl = 'http://localhost:80'; // Make sure this matches your server's URL
  const loginImageUrl = login ? `${serverBaseUrl}${login.image}` : null;

  // Effect to handle login state changes
  useEffect(() => {
    }, [login]);

  /**
   * Handle button click event
   * Logs out the user if they are logged in.
   */
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
          src={darkMode ? (login ? loginImageUrl : loginIconDarkMode) : (login ? loginImageUrl : loginIcon)}
          alt={login ? "logout" : "login"}
        />
        <span className="tooltip-text">{login ? "Log out" : "Login/ Sign Up"}</span>
      </button>
    </Link>
  );
}

export default LoginButton;
