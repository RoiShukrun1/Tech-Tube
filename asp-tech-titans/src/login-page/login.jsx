import React from 'react';
import './login.css'
import logo from '../images/Logo.png'
import { ReactComponent as ManIcon } from '../images/user.svg'; 
import { ReactComponent as LockIcon } from '../images/lock.svg'; 
import { Link, useNavigate } from 'react-router-dom';
import { useContext } from 'react';
import { UserContext } from '../contexts/userContext'; 
import { LoginContext } from '../contexts/loginContext.jsx';
import { ThemeContext } from '../contexts/themeContext';
import darkLogo from '../images/darkmodelogo.png'


export const Login = () => {
  const { users } = useContext(UserContext); 
  const { loggedIn } = useContext(LoginContext);
  const { darkMode } = useContext(ThemeContext);
  const navigate = useNavigate();
  const hundleLogin = (event) => {
    event.preventDefault();
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    const user = users.find(acc => acc.username === username && acc.password === password);
    // Check if the user exists
    if (!user) {
      alert('Incorrect password or username');
      return;
    } else {
      loggedIn(user); 
      alert('Login successful');
      navigate('/mainPage');
    }
  }
  
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
        <input type="submit" value="Login" onClick={hundleLogin} />
        <div>
        <p>Don't have an user? <Link to="/registration">Register</Link></p>
        <p>Continue as guest:   <Link to="/mainPage">Hompage</Link></p>               
        </div>
        </form>
    </div>
    </div>
  )
} 
export default  Login;
