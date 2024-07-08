import React from 'react';
import './videoPlayer.css';

// Video player component
const VideoPlayer = ({ url }) => {

  const baseServerUrl = 'http://localhost/';

  return (
    <video className="video-player" src={baseServerUrl + url} controls></video>
  );
};

export default VideoPlayer;
