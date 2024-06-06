import React from 'react';
import './addNewVideo.css';
import addVideoIcon from './add-video-icon.svg'; 

function AddNewVideo() {
  return (
    <button className="add-video-button">
      <img src={addVideoIcon} alt="Add Video" />
      <span className="tooltip-text">Create</span>
    </button>
  );
}

export default AddNewVideo;
