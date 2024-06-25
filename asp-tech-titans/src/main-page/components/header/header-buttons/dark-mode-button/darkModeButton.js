import React, { useContext } from 'react';
import darkModeIcon from '../../../../../db/icons/dark-mode-icon.svg';
import './darkModeButton.css';
import { ThemeContext } from '../../../../../contexts/themeContext';

/**
 * DarkModeButton Component
 * 
 * This component renders a button to toggle dark mode. It uses the ThemeContext to access the current 
 * theme state (darkMode) and the function to toggle the theme (toggleDarkMode). The button icon and 
 * styles change based on the current theme.
 */
function DarkModeButton() {
  // Retrieve darkMode value and toggleDarkMode function from ThemeContext
  const { darkMode, toggleDarkMode } = useContext(ThemeContext);

  return (
    <button 
      className={`dark-mode-button ${darkMode ? 'active' : ''}`} 
      onClick={toggleDarkMode}
    >
      <img 
        className='dark-mode-button-img' 
        src={darkModeIcon} 
        alt="Dark Mode" 
      />
      <span className="tooltip-text">Dark Mode</span>
    </button>
  );
}

export default DarkModeButton;
