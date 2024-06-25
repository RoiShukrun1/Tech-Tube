import React from 'react';
import './headerButtons.css';
import AddNewVideoButton from './add-new-video-button/addNewVideoButton';
import DarkModeButton from './dark-mode-button/darkModeButton';
import LoginButton from './login-button/loginButton';

/**
 * HeaderButtons Component
 * 
 * This component renders a group of buttons in the header: Dark Mode toggle, Add New Video, and Login/Logout.
 * Each button is imported from its respective component file.
 */
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
