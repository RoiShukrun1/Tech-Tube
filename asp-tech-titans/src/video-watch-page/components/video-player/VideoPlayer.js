import React from 'react';
import './VideoPlayer.css';

const VideoPlayer = ({ url }) => {
  return (
    <div>
      <video className="video-player" src={url} controls></video> 
    </div>
  );
};

export default VideoPlayer;
