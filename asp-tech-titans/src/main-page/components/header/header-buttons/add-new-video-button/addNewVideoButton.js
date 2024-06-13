import React from 'react';
import './addNewVideoButton.css';
import addVideoIcon from './add-video-icon.svg'; 

function AddNewVideoButton() {
  return (
    <button className="add-video-button">
      <img className="add-video-button-img" src={addVideoIcon} alt="Add Video" />
      <span className="tooltip-text">Create</span>
    </button>
  );
}

export default AddNewVideoButton;
