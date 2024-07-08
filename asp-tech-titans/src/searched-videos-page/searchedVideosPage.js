import 'bootstrap/dist/css/bootstrap.min.css';
import './searchedVideosPage.css'; 
import React, { useState, useEffect, useContext } from 'react';
import { useLocation } from 'react-router-dom';
import VideoThumbnail from '../main-page/components/video-thumbnail/videoThumbnail';
import Filters from '../main-page/components/filters/filters';
import Sidebar from '../main-page/components/side-bar/sideBar';
import Header from '../main-page/components/header/header';
import searchVideos from '../main-page/components/header/search-bar/searchVideos'; 
import { ThemeContext } from '../contexts/themeContext'; 
import { VideoDataContext } from '../contexts/videoDataContext';

function useQuery() {
  return new URLSearchParams(useLocation().search);
}

const SearchedVideosPage = () => {
  const { darkMode } = useContext(ThemeContext);
  const { videoData } = useContext(VideoDataContext);
  const [videos, setVideos] = useState(videoData);

  const query = useQuery().get('q');

  useEffect(() => {
    document.body.style.overflowX = 'hidden';
    return () => {
      document.body.style.overflowX = 'auto';
    };
  }, []);

  useEffect(() => {
    if (query) {
      const filteredVideos = searchVideos(videoData, query);
      setVideos(filteredVideos);
    } else {
      setVideos(videoData);
    }
  }, [query, videoData]);

  return (
    <div className={`container-fluid main-page ${darkMode ? 'dark' : ''}`}>
      <div className="row no-gutters">
        <div className="col-md-2 p-0">
          <Sidebar />
        </div>
        <div className="col-md-10 p-0">
          <Header onSearch={() => {}} /> {/* Optionally handle search within this page */}
          <div className="container-fluid p-0">
            <Filters />
            <div className="row no-gutters">
              {videos.map((newVideo, index) => (
                <div key={index} className="col-md-6 p-1">
                  <VideoThumbnail video={newVideo}/>
                </div>
              ))}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default SearchedVideosPage;
