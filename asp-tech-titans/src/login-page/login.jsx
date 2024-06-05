import React, { useState } from 'react';
import './login.css'
import logo from '../images/Logo.png'
import { ReactComponent as ManIcon } from '../images/user.svg'; 
import { ReactComponent as LockIcon } from '../images/lock.svg'; 

export const Login = () => {
  const searchDataInSessionStorage = (key, value) => {
    const data = JSON.parse(sessionStorage.getItem(key)) || [];
    const results = data.filter(item => {
      return item.username === value;
    });
    return results;
  };
  const hundleLogin = (event) => {
    event.preventDefault();
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    const searchResults = searchDataInSessionStorage('formData', username);
    if (searchResults.length === 0) {
      alert('User not found');
      return;
    }
    if (searchResults[0].password !== password) {
      alert('Incorrect password');
      return;
    }
    alert('Login successful');
    const logInInfo = {
      nickname: searchResults[0].nickname,
      image: searchResults[0].image
    };
    // Retrieve existing data from storage or initialize an empty array
    const info = JSON.parse(sessionStorage.getItem('loggedIn')) || [];
    // Add the new data to the existing array
    info.push(logInInfo);
    // Save the updated array back to storage
    sessionStorage.setItem('loggedIn', JSON.stringify(info));
  }
  
  return (
    <div className='login-container'>
        <img src={logo} alt="Logo" />
        <form>
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
        <p>Don't have an account? <a href="/registration">Register</a></p>
        </div>
        </form>
    </div>
  )
} 
export default  Login;
