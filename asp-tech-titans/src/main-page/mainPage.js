import React from 'react';
import VideoThumbnail from './components/video-thumbnail/videoThumbnail';
import jsonData from '../db/videos.json';
import 'bootstrap/dist/css/bootstrap.min.css';
import Filters from './components/filters/filters';
import Sidebar from './components/side-bar/sideBar';
import Header from './components/header/header';
import Recommended from './components/video-thumbnail/recommended-videos/recommended';

const MainPage = ({ setUrl }) => {
  return (
    <div className="container-fluid">
      <div className="row">
        <div className="col-md-2 p-0">
          <Sidebar />
        </div>
        <div className="col-md-10 p-0">
          <Header />
          <div className="container-fluid">
            <Filters />
            <Recommended />
            <div className="row">
              {jsonData.map((video, index) => (
                <div key={index} className="col-md-3 mb-4">
                  <VideoThumbnail video={video} onClick={() => setUrl(video.videoUrl)} />
                </div>
              ))}
            </div>
            <div className="row">
              {jsonData.map((video, index) => (
                <div key={index} className="col-md-3 mb-4">
                  <VideoThumbnail video={video} onClick={() => setUrl(video.videoUrl)} />
                </div>
              ))}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default MainPage;
