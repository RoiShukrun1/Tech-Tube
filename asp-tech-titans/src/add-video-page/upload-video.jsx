import React, { useState, useRef } from 'react';
import './upload-video.css';
import { ReactComponent as UploadIcon } from '../images/addVideo.svg'; // Make sure to replace this path with the correct path to your SVG
import { Link } from 'react-router-dom';
const UploadPage = () => {
  const [video, setVideo] = useState(null);
  const videoInputRef = useRef(null);

  const handleVideoUpload = (event) => {
    setVideo(URL.createObjectURL(event.target.files[0]));
  };

  const handleButtonClick = () => {
    videoInputRef.current.click();
  };
  
  return (
    <div className='upload-warpper'>
    <div className="containerAVPUpload">
      <div className={`upload-container ${video ? 'hidden' : ''}`}>
        <div className="upload-box">
          <label htmlFor="videoUpload" className="upload-label">
            <div className="upload-icon-container">
              {!video && <UploadIcon className="upload-icon" />}
            </div>
            <div className="upload-instructions">
              Drag and drop video files to upload
            </div>
            <input
              type="file"
              onChange={handleVideoUpload}
              ref={videoInputRef}
              id="videoUpload"
              accept="video/mp4"
              style={{ display: 'none' }}
            />
          </label>
        </div>
      </div>
      <button className="upload-button" type="button" onClick={handleButtonClick}>SELECT FILE</button>
      {video && <video src={video} className="video-preview" controls />}
      <Link to="/addVideo">
      <button className="next-button" type="button">Next</button>
      </Link>
    </div>
    </div>
  );
};

export default UploadPage;
