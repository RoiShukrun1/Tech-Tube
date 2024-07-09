import React, { useContext } from 'react';
import './login.css';
import logo from '../images/Logo.png';
import { ReactComponent as ManIcon } from '../images/user.svg';
import { ReactComponent as LockIcon } from '../images/lock.svg';
import { Link, useNavigate } from 'react-router-dom';
import { LoginContext } from '../contexts/loginContext.jsx';
import { ThemeContext } from '../contexts/themeContext';
import darkLogo from '../images/darkmodelogo.png';
import axios from 'axios';

export const Login = () => {
  const { loggedIn } = useContext(LoginContext);
  const { darkMode } = useContext(ThemeContext);
  const navigate = useNavigate();

  const handleLogin = async (event) => {
    event.preventDefault();
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    try {
      const response = await axios.post('http://localhost/api/token', { username, password }, { withCredentials: true });
      if (response.data.message === 'Login successful') {
        const { username, image } = response.data.user;
        loggedIn({ username, image }); // Update login context with image
        alert('Login successful');
        navigate('/mainPage'); // Navigate to main page
      } else {
        alert('Login failed');
      }
    } catch (error) {
      if (error.response && error.response.data && error.response.data.message) {
        alert(error.response.data.message);
      } else {
        alert('Error logging in');
      }}
  };

  return (
    <div className={'login-wrapper' + (darkMode ? '-dark' : '')}>
      <div className='login-container'>
        <img className='login-img' src={darkMode ? darkLogo : logo} alt="Logo" /> {/* Conditional logo */}
        <form className='login-form'>
          <div>
            <ManIcon className="ManIcon" />
            <input type="text" id="username" name="username" placeholder="Username" />
          </div>
          <div>
            <LockIcon className="LockIcon" />
            <input type="password" id="password" name="password" placeholder="Password" />
          </div>
          <input type="submit" value="Login" onClick={handleLogin} />
          <div>
            <p>Don't have an user? <Link to="/registration">Register</Link></p>
            <p>Continue as guest: <Link to="/mainPage">Homepage</Link></p>
          </div>
        </form>
      </div>
    </div>
  );
};

export default Login;
