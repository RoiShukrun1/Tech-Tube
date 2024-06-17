import React, { useContext, useState, useEffect } from 'react';
import { CurrentVideoContext } from './currentVideoContext';
import VideoPlayer from './components/video-player/VideoPlayer';
import VideoInfo from './components/video-info/videoInfo';
import Comments from './components/comments/comments';
import RelatedVideos from './components/related-videos/relatedVideos';
import jsonData from '../db/videos.json';
import usersData from "../db/users.json";
import Header from '../main-page/components/header/header';
import ScrollingMenuButton from '../main-page/components/side-bar/scrolling-menu-button/scrollingMenuButton';
import ScrollingMenu from '../main-page/components/side-bar/scrolling-menu/scrollingMenu';
import { LoginContext } from '../contexts/loginContext';

function getUserObjById(usersData, id) {
  return usersData.find(obj => obj.id === id);
}

function getObjectByUrl(jsonData, url) {
  return jsonData.find(obj => obj.videoUploaded === url);
}

const VideoWatchPage = () => {

  const { videoUrl, setVideoUrl } = useContext(CurrentVideoContext);
  const { login } = useContext(LoginContext);

  useEffect(() => {
    if (videoUrl) {
      setVideoUrl(videoUrl);
    }
  }, [videoUrl]);

  const toggleMenu = () => {
    setIsMenuOpen(!isMenuOpen);
  };

  const [isMenuOpen, setIsMenuOpen] = useState(false); 
  const [users, setUsers] = useState(usersData); // the current user for now is the first user in the users.json file
  const [videos, setVideos] = useState(jsonData);
  const [isFocused, setIsFocused] = useState(false);
  const [inputValue, setInputValue] = useState('');
  const [moreInfoPressed, setMoreInfoPressed] = useState(false);
  

  const currentVideo = getObjectByUrl(videos, videoUrl);
  const currentUser = getUserObjById(users, 1);

  console.log(currentUser)
  console.log(login)

  
  const handleSearch = (query) => {
    const filteredVideos = videos.filter(video => video.title.toLowerCase().includes(query.toLowerCase()));
    if (filteredVideos.length === 0) {
      return;
    }
    setVideoUrl(filteredVideos[0].videoUploaded);
  };

  return (
    <div>
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
                setUsers={setUsers}
                setMoreInfoPressed={setMoreInfoPressed}
                moreInfoPressed={moreInfoPressed}
              />
              <Comments
                comments={currentVideo.comments}
                currentUser={currentUser}
                setVideos={setVideos}
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
              relatedVideos={currentVideo.relatedVideos}
              setUrl={setVideoUrl}
              setVideos={setVideos}
              setMoreInfoPressed={setMoreInfoPressed}
              videos={videos}
              setInputValue={setInputValue}
            />
          </div>
        </div>
      </div>

    </div>

  );
};

export default VideoWatchPage;

