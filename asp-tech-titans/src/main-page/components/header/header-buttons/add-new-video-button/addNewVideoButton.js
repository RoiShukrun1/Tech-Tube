import React, { useContext } from 'react';
import './addNewVideoButton.css';
import addVideoIcon from './add-video-icon.svg'; 
import addVideoIconDm from '../../../../../db/icons/add-video-dm.svg'; 
import { Link } from 'react-router-dom';
import { ThemeContext } from '../../../../../contexts/themeContext';

/**
 * AddNewVideoButton Component
 * 
 * This component renders a button for adding a new video. It uses React Router's Link component 
 * to navigate to the upload page. The button icon changes based on the current theme (dark or light mode).
 */
function AddNewVideoButton() {
  // Retrieve the darkMode value from ThemeContext
  const { darkMode } = useContext(ThemeContext);

  return (
    <Link to="/uploadPage" className="add-video-link">
      <button className="add-video-button">
        {/* Display the appropriate icon based on the current theme */}
        <img 
          className="add-video-button-img" 
          src={darkMode ? addVideoIconDm : addVideoIcon} 
          alt="Add Video" 
        />
        {/* Tooltip text displayed on hover */}
        <span className="tooltip-text">Create</span>
      </button>
    </Link>
  );
}

export default AddNewVideoButton;
