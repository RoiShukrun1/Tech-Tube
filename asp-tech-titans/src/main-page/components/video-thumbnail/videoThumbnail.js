import React from 'react';
import './videoThumbnail.css';

const VideoThumbnail = ({ video, onClick }) => {

  return (
    <div className="video-thumbnail" onClick={onClick}>
      <img src={video.imgUrl} alt={video.videoTitle} />
      <div className="video-info">
        <h3>{video.videoTitle}</h3>
        <p>{video.publisher}</p>
        <p>{video.views} views</p>
        <p>{video.date}</p>
      </div>
    </div>
  );
};

export default VideoThumbnail;

