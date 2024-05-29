// src/pages/video-watch-page.js
import React from 'react';
import VideoPlayer from './components/video-player/VideoPlayer';
// import VideoInfo from '../components/VideoInfo';
// import RelatedVideos from '../components/RelatedVideos';
// import Comments from '../components/Comments';

const VideoWatchPage = ({ videoUrl }) => {
//   const videoTitle = 'Sample Video Title';
//   const videoDescription = 'This is a sample video description.';
//   const relatedVideos = ['Related video 1', 'Related video 2', 'Related video 3'];
//   const comments = ['User1: Comment 1', 'User2: Comment 2', 'User3: Comment 3'];

  return (
    <div className="video-watch-page">
        <h1 style={{ textAlign: 'center'}}>Video Watch Page</h1>
      <VideoPlayer url={videoUrl}/>
      {/* <VideoInfo title={videoTitle} description={videoDescription} />
      <RelatedVideos videos={relatedVideos} />
      <Comments comments={comments} /> */}
    </div>
  );
};

export default VideoWatchPage;
