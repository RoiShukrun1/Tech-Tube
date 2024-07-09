import 'bootstrap/dist/css/bootstrap.min.css';
import './mainPage.css'; 
import React, { useState, useEffect, useContext } from 'react';
import { useLocation } from 'react-router-dom';
import VideoThumbnail from './components/video-thumbnail/videoThumbnail';
import Filters from './components/filters/filters';
import Sidebar from './components/side-bar/sideBar';
import Header from './components/header/header';
import searchVideos from './components/header/search-bar/searchVideos'; 
import { ThemeContext } from '../contexts/themeContext'; 
import { VideoDataContext } from '../contexts/videoDataContext';
import axios from 'axios';

const MainPage = () => {
  const location = useLocation();
  const { darkMode } = useContext(ThemeContext);
  const { videoData, setVideoData, refreshVideoData } = useContext(VideoDataContext);
  const [videos, setVideos] = useState([videoData]);


  const fetch20VideoList = async () => {
    try {
        const response = await axios.get('http://localhost/api/videos');
        console.log(response.data);
        setVideos(response.data);
    } catch (error) {
        console.error('Error fetching video list:', error);
    }
  };

  const handleDelete = (videoId) => {
    const updatedVideos = videos.filter(video => video.id !== videoId);
    setVideos(updatedVideos);
    setVideoData(updatedVideos);
    refreshVideoData();
  };

  useEffect(() => {
    document.body.style.overflowX = 'hidden';
    return () => {
      document.body.style.overflowX = 'auto';
    };
  }, []);

  useEffect(() => {
    if (location.state && location.state.refresh) {
      refreshVideoData();
    }
  }, [location.state, refreshVideoData]);
  
  useEffect(() => {
    fetch20VideoList();
  }, []);
  
  return (
    <div className={`container-fluid main-page ${darkMode ? 'dark' : ''}`}>
      <div className="row no-gutters">
        <div className="col-md-2 p-0">
          <Sidebar />
        </div>
        <div className="col-md-10 p-0">
          <Header/>
          <div className="container-fluid p-0">
            <Filters />
            <div className="row no-gutters">
              {videos.map((newVideo, index) => (
                <div key={index} className="col-md-4 p-1">
                  <VideoThumbnail video={newVideo} onDelete={handleDelete}/>
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
