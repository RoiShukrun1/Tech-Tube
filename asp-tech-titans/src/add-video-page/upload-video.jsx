import React, { useState, useRef, useContext } from 'react';
import './upload-video.css';
import { ReactComponent as UploadIcon } from '../images/addVideo.svg'; 
import { Link } from 'react-router-dom';
import { VideoContext } from '../contexts/videoContext'; // Import the VideoContext
import LoginValidation from '../app/loginValidation';


const UploadPage = () => {
  const [video, setVideo] = useState(null);
  const [message, setMessage] = useState(''); // Add state for message
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
    setMessage('Video uploaded successfully!');
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
        {message && <p>{message}</p>}
        <Link to="/addVideo">
          <button className="next-button" type="button">Next</button>
        </Link>
        <Link to="/videoList">
          <button className="test-button" type="button">test</button>
        </Link>
      </div>
    </div>
  );
};

export default UploadPage;
