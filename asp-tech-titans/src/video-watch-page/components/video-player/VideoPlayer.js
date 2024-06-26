import React from 'react';
import './VideoPlayer.css';

// Video player component
const VideoPlayer = ({ url }) => {
  return (
    <video className="video-player" src={url} controls></video>
  );
};

export default VideoPlayer;
