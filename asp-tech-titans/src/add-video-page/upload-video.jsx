import React, { useState, useRef, useContext } from 'react';
import './upload-video.css';
import { ReactComponent as UploadIcon } from '../images/addVideo.svg'; 
import { useNavigate,Link } from 'react-router-dom';
import { VideoContext } from '../contexts/videoContext'; // Import the VideoContext
import { ThemeContext } from '../contexts/themeContext';


const UploadPage = () => {
  const [video, setVideo] = useState(null);
  const videoInputRef = useRef(null);
  const { addNewVideo } = useContext(VideoContext);
  const { darkMode } = useContext(ThemeContext);
  const navigate = useNavigate();
  // Function to handle the video upload
  const handleVideoUpload = (event) => {
    const file = event.target.files[0];
    if (file) {
      const videoURL = URL.createObjectURL(file);
      setVideo(videoURL);

      const videoObject = {
        file,
        url: videoURL,
      };
      addNewVideo(videoObject);
    }
  };
  // Function to handle the button click
  const handleButtonClick = () => {
    videoInputRef.current.click();
  };
  // Function to handle the next button click
  const handleNextClick = () => {
    if (video) {
      navigate('/addVideo');
    } else {
      alert("Please upload a video before proceeding to the next page.");
    }
  };

  return (
    <div className={`upload-warpper ${darkMode ? 'upload-warpper-dark' : ''}`}>
      <div className={`containerAVPUpload ${darkMode ? 'containerAVPUpload-dark' : ''}`}>
        <div className={`upload-container ${video ? 'hidden' : ''}`}>
          <div className={`upload-box ${darkMode ? 'upload-box-dark' : ''}`}>
            <label htmlFor="videoUpload" className="upload-label">
              <div className="upload-icon-container">
                {!video && <UploadIcon className="upload-icon" />}
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
        <button className="next-button" type="button" onClick={handleNextClick}>Next</button>
        <Link to="/mainPage">
          <button className="back-button" type="button">Back</button>
        </Link>
      </div>
    </div>
  );
};

export default UploadPage;
