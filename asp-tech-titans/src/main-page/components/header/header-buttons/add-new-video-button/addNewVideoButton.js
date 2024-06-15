import React from 'react';
import './addNewVideoButton.css';
import addVideoIcon from './add-video-icon.svg'; 
import { Link } from 'react-router-dom';

function AddNewVideoButton() {
  return (
    <Link to="/uploadPage">
      <button className="add-video-button">
        <img className="add-video-button-img" src={addVideoIcon} alt="Add Video" />
        <span className="tooltip-text">Create</span>
      </button>
    </Link>
  );
}

export default AddNewVideoButton;
