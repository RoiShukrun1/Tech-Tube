import React from 'react';
import { Routes, Route } from 'react-router-dom';
import MainPage from '../main-page/mainPage';
import Login from '../login-page/login';
import Registration from '../registration-page/registration';
import VideoWatchPage from '../video-watch-page/videoWatchPage';
import UploadPage from '../add-video-page/upload-video';
import AddPage from '../add-video-page/add-video';
import { VideoProvider } from '../contexts/videoContext'; 
import { UserProvider } from '../contexts/userContext'; 
import { LoginProvider } from '../contexts/loginContext'; 
import { VideoDataProvider } from '../contexts/videoDataContext';
import LoginValidation from './loginValidation';
import { CurrentVideoProvider } from '../video-watch-page/currentVideoContext';
import PublisherChannelPage from '../publisher-chanel-page/publisherChannelPage';
import { CurrentPublisherProvider } from '../publisher-chanel-page/currentPublisherContext';


const AppRoutes = () => {
  return (
    <VideoDataProvider>
      <LoginProvider>
        <UserProvider>
          <VideoProvider>
            <CurrentVideoProvider>
              <CurrentPublisherProvider>
              <Routes>
                <Route path="/" element={<MainPage />} />
                <Route path="/registration" element={<Registration />} />
                <Route path="/login" element={<Login />} />
                <Route path="/mainPage" element={<MainPage />} />
                <Route path="/video" element={<VideoWatchPage />} />
                <Route path="/uploadPage" element={<LoginValidation><UploadPage /></LoginValidation>} />
                <Route path="/addVideo" element={<LoginValidation><AddPage /></LoginValidation>} />
                <Route path="/publisherChannel" element={<PublisherChannelPage />} />
              </Routes>
              </CurrentPublisherProvider>
            </CurrentVideoProvider>
          </VideoProvider>
        </UserProvider>
      </LoginProvider>
    </VideoDataProvider>
  );
};

export default AppRoutes;
