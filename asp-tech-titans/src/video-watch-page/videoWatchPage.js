import React, { useContext, useState, useEffect } from 'react';
import { CurrentVideoContext } from './currentVideoContext';
import VideoPlayer from './components/video-player/videoPlayer';
import VideoInfo from './components/video-info/videoInfo';
import Comments from './components/comments/comments';
import RelatedVideos from './components/related-videos/relatedVideos';
import Header from '../main-page/components/header/header';
import ScrollingMenuButton from '../main-page/components/side-bar/scrolling-menu-button/scrollingMenuButton';
import ScrollingMenu from '../main-page/components/side-bar/scrolling-menu/scrollingMenu';
import { LoginContext } from '../contexts/loginContext';
import { VideoDataContext } from '../contexts/videoDataContext';
import { ThemeContext } from '../contexts/themeContext';
import './videoWatchPage.css';
import { Navigate } from 'react-router-dom';

// Function to get video object by URL from video data
function getObjectByUrl(jsonData, url) {
  return jsonData.find(obj => obj.videoUploaded === url);
}

const VideoWatchPage = () => {

  // Destructuring values from various contexts
  const { darkMode } = useContext(ThemeContext);
  const { videoUrl, setVideoUrl } = useContext(CurrentVideoContext);
  const { login } = useContext(LoginContext);
  const { videoData, setVideoData } = useContext(VideoDataContext);

  const [isLoading, setIsLoading] = useState(true);
  const [message, setMessage] = useState('Loading...');

  useEffect(() => {
    if(videoData.length === 0) {
      return;
    }
    if(!videoUrl) {
      setMessage('Please select a video from main page.');
      return;
    }
    setIsLoading(false);
  }, [videoData]);

  // Effect to set the video URL whenever it changes
  useEffect(() => {
    if (videoUrl) {
      setVideoUrl(videoUrl);
    }
  }, [videoUrl, setVideoUrl]);
  
  const [isMenuOpen, setIsMenuOpen] = useState(false);

  // State to manage input focus
  const [isFocused, setIsFocused] = useState(false);

  // State to manage input value
  const [inputValue, setInputValue] = useState('');

  // State to manage "more info" button press
  const [moreInfoPressed, setMoreInfoPressed] = useState(false);

  // Get the current video object by URL
  const currentVideo = getObjectByUrl(videoData, videoUrl);

  const currentUser = login;

  // Function to handle search and filter videos
  const handleSearch = async (query) => {

    const filteredVideos = videoData.filter(video => video.title.toLowerCase().includes(query.toLowerCase()));
    if (filteredVideos.length === 0) {
      return;
    }
    setVideoUrl(filteredVideos[0].videoUploaded);
  };

  // Toggle function for the menu
  const toggleMenu = () => {
    setIsMenuOpen(!isMenuOpen);
  };

  return (
    isLoading ? (message==='Loading...' ? <div>{message}</div> : <Navigate to="/mainPage" />) :
    <div className={`video-watch-page ${darkMode ? 'dark' : ''}`}>
      <Header onSearch={handleSearch} />
      <ScrollingMenuButton isOpen={isMenuOpen} toggleMenu={toggleMenu} />
      <ScrollingMenu isOpen={isMenuOpen} toggleMenu={toggleMenu} />
      <div className="container">
        <div className="row">
          <div className="col">
            <div className="video-watch-page">
              <VideoPlayer url={videoUrl} />
              <VideoInfo
                currentVideo={currentVideo}
                currentUser={currentUser}
                setMoreInfoPressed={setMoreInfoPressed}
                moreInfoPressed={moreInfoPressed}
              />
              <Comments
                comments={currentVideo.comments}
                currentUser={currentUser}
                setVideos={setVideoData}
                currentVideoId={currentVideo.id}
                inputValue={inputValue}
                setInputValue={setInputValue}
                isFocused={isFocused}
                setIsFocused={setIsFocused}
                login={login} />
            </div>
          </div>
          <div className="col-5">
            <RelatedVideos
              setMoreInfoPressed={setMoreInfoPressed}
              setInputValue={setInputValue}
            />
          </div>
        </div>
      </div>
    </div>
  );
};

export default VideoWatchPage;
