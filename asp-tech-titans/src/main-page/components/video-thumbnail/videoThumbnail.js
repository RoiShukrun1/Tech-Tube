import React, { useState, useContext } from 'react';
import './videoThumbnail.css';
import { Link } from 'react-router-dom';
import { LoginContext } from '../../../contexts/loginContext'; 
import { VideoDataContext } from '../../../contexts/videoDataContext';


const VideoThumbnail = ({ video, onClick}) => {
  const [isHovered, setIsHovered] = useState(false);
  const { login } = useContext(LoginContext);
  const {videoData, deleteVideo} = useContext(VideoDataContext)

  const handleMouseEnter = () => {
    setIsHovered(true);
  };

  const handleMouseLeave = () => {
    setIsHovered(false);
  };

  const handleDeleteClick = (e) => {
    e.preventDefault();
    deleteVideo(video.id);
  };

  return (
    <Link to="/video">
      <div
        className="video-thumbnail"
        onClick={onClick}
        onMouseEnter={handleMouseEnter}
        onMouseLeave={handleMouseLeave}
      >
        <img src={video.thumbnail} alt={video.title} className="thumbnail-image" />
        <div className="video-info">
          <div className="video-header">
            <img src={video.publisherImage} alt={video.publisher} className="publisher-image" />
            <h3 className="video-title">{video.title}</h3>
            {login && login.username === video.publisher && (
              <button className="delete-button" onClick={handleDeleteClick}>
                &#x1F5D1;
              </button>
            )}
          </div>
          <div className="video-details">
            <p className="video-views">{video.views} views • <span className="video-date">{video.date}</span></p>
            <p className="video-publisher">{video.publisher}</p>
          </div>
        </div>
      </div>
    </Link>
  );
};

export default VideoThumbnail;
