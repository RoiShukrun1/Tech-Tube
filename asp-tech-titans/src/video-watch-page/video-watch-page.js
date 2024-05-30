import React from 'react';
import VideoPlayer from './components/video-player/VideoPlayer';
import VideoInfo from './components/video-info/videoInfo';
import Comments from './components/comments/comments';
import jsonData from '../db/videos.json';

function getObjectByUrl(jsonData, url) {
  return jsonData.find(obj => obj.videoUrl === url);
}

const VideoWatchPage = ({ videoUrl }) => {

  const videoCorrispondingData = getObjectByUrl(jsonData, videoUrl); 

  return (

    <div className="container text-center">
      <div className="row">
        <div className="col">

          <div className="video-watch-page">
            <VideoPlayer url={videoUrl} />
            <VideoInfo videoTitle={videoCorrispondingData.videoTitle}
              views={videoCorrispondingData.views}
              date={videoCorrispondingData.date} />
            <Comments comments={videoCorrispondingData.Comments}  />


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

