import React from 'react';
import './VideoPlayer.css';

const VideoPlayer = ({ url }) => {
  return (
    <div
      className="video-player">
      <video src={url} controls/>
    </div>
  );
};

export default VideoPlayer;
