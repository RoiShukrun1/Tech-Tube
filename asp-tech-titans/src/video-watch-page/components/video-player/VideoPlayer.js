import React from 'react';
import ReactPlayer from 'react-player';
import './VideoPlayer.css';

const VideoPlayer = ({ url }) => {
  const videoUrl = url.startsWith('/') ? process.env.PUBLIC_URL + url : url;

  return (
    <div className="video-player">
      <ReactPlayer url={videoUrl}
      controls
      />
    </div>
  );
};

export default VideoPlayer;
