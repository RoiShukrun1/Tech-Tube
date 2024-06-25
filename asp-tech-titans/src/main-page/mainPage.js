import 'bootstrap/dist/css/bootstrap.min.css';
import './mainPage.css'; 
import React, { useState, useEffect, useContext } from 'react';
import VideoThumbnail from './components/video-thumbnail/videoThumbnail';
import Filters from './components/filters/filters';
import Sidebar from './components/side-bar/sideBar';
import Header from './components/header/header';
import searchVideos from './components/header/search-bar/searchVideos'; 
import { incrementViews } from '../video-watch-page/components/related-videos/videoCard';
import { CurrentVideoContext } from '../video-watch-page/currentVideoContext';
import { ThemeContext } from '../contexts/themeContext'; 
import { VideoDataContext } from '../contexts/videoDataContext';

function getObjectByUrl(jsonData, url) {
  return jsonData.find(obj => obj.videoUploaded === url);
}

const MainPage = () => {

  const { setVideoUrl } = useContext(CurrentVideoContext);
  const { darkMode } = useContext(ThemeContext);
  const { videoData, setVideoData } = useContext(VideoDataContext);
  const [videos, setVideos] = useState(videoData);

  const handleSearch = (query) => {
    const filteredVideos = searchVideos(videoData, query);
    setVideos(filteredVideos);
  };

  const handleThumbnailClick = (videoUrl) => {
    setVideoUrl(videoUrl);
    incrementViews(setVideoData, getObjectByUrl(videoData, videoUrl));
  };

  useEffect(() => {
    document.body.style.overflowX = 'hidden';
    return () => {
      document.body.style.overflowX = 'auto';
    };
  }, []);

  useEffect(() => {
    setVideos(videoData);
  }, [videoData]);

  return (
    <div className={`container-fluid main-page ${darkMode ? 'dark' : ''}`}>
      <div className="row no-gutters">
        <div className="col-md-2 p-0">
          <Sidebar />
        </div>
        <div className="col-md-10 p-0">
          <Header onSearch={handleSearch} />
          <div className="container-fluid p-0">
            <Filters />
            <div className="row no-gutters">
              {videos.map((newVideo, index) => (
                <div key={index} className="col-md-4 p-1">
                  <VideoThumbnail video={newVideo} onClick={() => handleThumbnailClick(newVideo.videoUploaded)} />
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
