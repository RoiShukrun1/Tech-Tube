import React from 'react';
import './headerButtons.css';
import AddNewVideoButton from './add-new-video-button/addNewVideoButton';
import DarkModeButton from './dark-mode-button/darkModeButton';
import LoginButton from './login-button/loginButton';
import { Link, useNavigate } from 'react-router-dom';

function HeaderButtons() {
  return (
    <div className="header-buttons">
      <DarkModeButton />
      <AddNewVideoButton />
      <LoginButton />
    </div>
  );
}

export default HeaderButtons;
