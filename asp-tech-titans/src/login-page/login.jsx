import React from 'react';
import './login.css'
import logo from '../images/Logo.png'
import { ReactComponent as ManIcon } from '../images/user.svg'; 
import { ReactComponent as LockIcon } from '../images/lock.svg'; 
import { Link, useNavigate } from 'react-router-dom';
import { useContext } from 'react';
import { AccountContext } from '../contexts/accountContext'; 
import { LoginContext } from '../contexts/loginContext.jsx';
import { ThemeContext } from '../contexts/themeContext';
import darkLogo from '../images/darkmodelogo.png'
import axios from 'axios';


export const Login = () => {
  const { accounts } = useContext(AccountContext); 
  const { loggedIn } = useContext(LoginContext);
  const { darkMode } = useContext(ThemeContext);
  const navigate = useNavigate();
  const hundleLogin = async (event) => {
    event.preventDefault();
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    // Fetch the account from the server
    var account = (await axios.get('http://localhost/api/users/' + username)).data;
    // Check if the password is correct
    if (account && account.password !== password) {
      account = null;
    }

    // const account = accounts.find(acc => acc.username === username && acc.password === password);

    // Check if the account exists
    if (!account) {
      alert('Incorrect password or username');
      return;
    } else {
      loggedIn(account); 
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
        <p>Don't have an account? <Link to="/registration">Register</Link></p>
        <p>Continue as guest:   <Link to="/mainPage">Hompage</Link></p>               
        </div>
        </form>
    </div>
    </div>
  )
} 
export default  Login;
