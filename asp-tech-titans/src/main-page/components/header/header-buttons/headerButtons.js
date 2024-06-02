import React from 'react';
import './headerButtons.css';
import AddNewVideo from './add-new-video/addNewVideo';

function HeaderButtons() {
  return (
    <div className="header-buttons">
      <button className="dark-mode-button">+ Dark Mode</button>
      <AddNewVideo />
      <button className="login-button">Log In</button>
    </div>
  );
}

export default HeaderButtons;
