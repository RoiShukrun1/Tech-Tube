import React from 'react';
import darkModeIcon from '../../../../../db/icons/dark-mode-icon.svg';
import './darkModeButton.css'

function DarkModeButton() {
  return (
    <button className="dark-mode-button">
      <img className='dark-mode-button-img' src={darkModeIcon} alt="Dark Mode" />
      <span className="tooltip-text">Dark Mode</span>
    </button>
  );
}

export default DarkModeButton;
