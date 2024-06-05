import React, { useState } from 'react';
import VideoThumbnail from './components/video-thumbnail/videoThumbnail';
import jsonData from '../db/videos.json';
import 'bootstrap/dist/css/bootstrap.min.css';
import Filters from './components/filters/filters';
import Sidebar from './components/side-bar/sideBar';
import Header from './components/header/header';
import searchVideos from './components/header/search-bar/searchVideos'; 

const MainPage = ({ setUrl }) => {
  const [videos, setVideos] = useState(jsonData);

  const handleSearch = (query) => {
    const filteredVideos = searchVideos(jsonData, query);
    setVideos(filteredVideos);
  };

  return (
    <div className="container-fluid">
      <div className="row no-gutters">
        <div className="col-md-2 p-0">
          <Sidebar />
        </div>
        <div className="col-md-10 p-0">
          <Header onSearch={handleSearch} />
          <div className="container-fluid p-0">
            <Filters />
            <div className="row no-gutters">
              {videos.map((video, index) => (
                <div key={index} className="col-md-3 p-1">
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
