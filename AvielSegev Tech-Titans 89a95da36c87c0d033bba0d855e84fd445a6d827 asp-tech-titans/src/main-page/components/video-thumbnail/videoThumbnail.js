import React, { useState } from 'react';
import './videoThumbnail.css';
import playIcon from '../../../db/icons/play-button-arrowhead-svgrepo-com.svg'

const VideoThumbnail = ({ video, onClick }) => {
  const [isHovered, setIsHovered] = useState(false);

  const handleMouseEnter = () => {
    setIsHovered(true);
  };

  const handleMouseLeave = () => {
    setIsHovered(false);
  };

  return (
    <div
      className="video-thumbnail"
      onClick={onClick}
      onMouseEnter={handleMouseEnter}
      onMouseLeave={handleMouseLeave}
    >
       <img src={video.imgUrl} alt={video.videoTitle} />
      <div className={`play-icon ${isHovered ? 'visible' : ''}`}>
        <img src={playIcon} alt="Play Icon" />
      </div>
      <div className="video-info">
        <h3 className="video-title">{video.videoTitle}</h3>
        <p className="video-publisher">{video.publisher}</p>
        <p className="video-views">{video.views} views • <span className="video-date">{video.date}</span></p>
      </div>
    </div>
  );
};

export default VideoThumbnail;
