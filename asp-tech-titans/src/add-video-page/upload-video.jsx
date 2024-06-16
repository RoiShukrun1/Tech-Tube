import React, { useState, useRef, useContext } from 'react';
import './upload-video.css';
import { ReactComponent as UploadIcon } from '../images/addVideo.svg'; 
import { Link } from 'react-router-dom';
import { VideoContext } from '../contexts/videoContext'; // Import the VideoContext
import LoginValidation from '../app/loginValidation';


const UploadPage = () => {
  const [video, setVideo] = useState(null);
  const videoInputRef = useRef(null);
  const { addNewVideo } = useContext(VideoContext);

  const handleVideoUpload = (event) => {
    const file = event.target.files[0];
    const videoURL = URL.createObjectURL(file);
    setVideo(videoURL);

    // Create a video object to store in context
    const videoObject = {
      file,
      url: videoURL,
    };

    // Add the video object to the global context
    addNewVideo(videoObject);
  };

  const handleButtonClick = () => {
    videoInputRef.current.click();
  };

  return (
    <div className='upload-warpper'>
      <div className="containerAVPUpload">
        <div className={`upload-container ${videoList ? 'hidden' : ''}`}>
          <div className="upload-box">
            <label htmlFor="videoUpload" className="upload-label">
              <div className="upload-icon-container">
                {!videoList && <UploadIcon className="upload-icon" />}
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
        <Link to="/mainPage">
        <button className="back-button"type="button">Back</button>
        </Link>
      </div>
    </div>
  );
};

export default UploadPage;
