import React, { useContext } from 'react';
import darkModeIcon from '../../../../../db/icons/dark-mode-icon.svg';
import './darkModeButton.css';
import { ThemeContext } from '../../../../../contexts/themeContext';

function DarkModeButton() {
  const { darkMode, toggleDarkMode } = useContext(ThemeContext);

  return (
    <button className={`dark-mode-button ${darkMode ? 'active' : ''}`} onClick={toggleDarkMode}>
      <img className='dark-mode-button-img' src={darkModeIcon} alt="Dark Mode" />
      <span className="tooltip-text">Dark Mode</span>
    </button>
  );
}

export default DarkModeButton;
