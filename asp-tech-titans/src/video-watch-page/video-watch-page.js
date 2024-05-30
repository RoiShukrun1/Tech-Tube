// src/pages/video-watch-page.js
import React from 'react';
import VideoPlayer from './components/video-player/VideoPlayer';
import VideoInfo from './components/video-info/videoInfo';
// import RelatedVideos from '../components/RelatedVideos';
// import Comments from '../components/Comments';

const VideoWatchPage = ({ videoUrl }) => {
  //   const videoTitle = 'Sample Video Title';
  //   const videoDescription = 'This is a sample video description.';
  //   const relatedVideos = ['Related video 1', 'Related video 2', 'Related video 3'];
  //   const comments = ['User1: Comment 1', 'User2: Comment 2', 'User3: Comment 3'];
  {/* <VideoInfo title={videoTitle} description={videoDescription} />
      <RelatedVideos videos={relatedVideos} />
      <Comments comments={comments} /> */}


  return (

    <div className="container text-center">
      <div className="row">
        <div className="col">

          <div className="video-watch-page">
            <VideoPlayer url={videoUrl} />
            <VideoInfo videoTitle="Sample Video Title" views="100" date="2021-10-01" />


          </div>
        </div>

        <div className="col">
          <h1>ALong video</h1>


        </div>
      </div>
    </div>





  );
};

export default VideoWatchPage;
