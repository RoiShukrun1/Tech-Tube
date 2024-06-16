import React, { useContext } from 'react';
import './addNewVideoButton.css';
import addVideoIcon from './add-video-icon.svg'; 
import addVideoIconDm from '../../../../../db/icons/add-video-dm.svg'; 
import { Link } from 'react-router-dom';
import { ThemeContext } from '../../../../../contexts/themeContext';


function AddNewVideoButton() {
  const { darkMode } = useContext(ThemeContext);

  return (
    <Link to="/uploadPage" className="add-video-link">
      <button className="add-video-button">
        <img className="add-video-button-img" src={darkMode ? addVideoIconDm : addVideoIcon} alt="Add Video" />
        <span className="tooltip-text">Create</span>
      </button>
    </Link>
  );
}

export default AddNewVideoButton;
