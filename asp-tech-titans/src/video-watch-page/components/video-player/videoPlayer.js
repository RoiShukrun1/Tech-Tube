import React from 'react';
import './videoPlayer.css';

// Video player component
const VideoPlayer = ({ url }) => {
  return (
    <video className="video-player" src={url} controls></video>
  );
};

export default VideoPlayer;
