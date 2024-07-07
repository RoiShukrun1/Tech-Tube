import 'bootstrap/dist/css/bootstrap.min.css';
import './publisherChannelPage.css'; 
import React, { useState, useEffect, useContext } from 'react';
import VideoThumbnail from '../main-page/components/video-thumbnail/videoThumbnail';
import Sidebar from '../main-page/components/side-bar/sideBar';
import Header from '../main-page/components/header/header';
import { incrementViews } from '../video-watch-page/components/related-videos/videoCard';
import { CurrentVideoContext } from '../video-watch-page/currentVideoContext';
import { ThemeContext } from '../contexts/themeContext'; 
import { VideoDataContext } from '../contexts/videoDataContext';
import { LoginContext } from '../contexts/loginContext';
import { AccountContext } from '../contexts/accountContext';
import PublisherInfo from './components/publisherInfo/publisherInfo';
import publisherImage from '../db/techTitansLogo.png'
import publisherBanner from '../db/8.jpg'

function getObjectByUrl(jsonData, url) {
    return jsonData.find(obj => obj.videoUploaded === url);
}

const PublisherChannelPage = () => {

  const { setVideoUrl } = useContext(CurrentVideoContext);
  const { darkMode } = useContext(ThemeContext);
  const { videoData, setVideoData } = useContext(VideoDataContext);
  const [videos, setVideos] = useState(videoData);
  const { login } = useContext(LoginContext);
  const { setAccounts } = useContext(AccountContext);

  const publisherData = {
    username: 'username',
    subscribers: '44.6K',
    videos: '1.8K',
    nickname: 'mta', 
    bannerUrl: publisherBanner,
    logoUrl: publisherImage,
    currentUser: login, 
    setUsers: setAccounts,
  };


  // Function to handle search and filter videos
  const handleSearch = (query) => {
    const filteredVideos = videoData.filter(video => video.title.toLowerCase().includes(query.toLowerCase()));
    if (filteredVideos.length === 0) {
      return;
    }
    setVideoUrl(filteredVideos[0].videoUploaded);
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
          <div><PublisherInfo {...publisherData}></PublisherInfo></div>
          <div className="container-fluid p-0">
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

export default PublisherChannelPage;
