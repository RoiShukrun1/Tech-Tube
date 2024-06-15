import React from 'react';
import { Routes, Route } from 'react-router-dom';
import MainPage from '../main-page/mainPage';
import Login from '../login-page/login';
import Registration from '../registration-page/registration';
import VideoWatchPage from '../video-watch-page/video-watch-page';
import UploadPage from '../add-video-page/upload-video';
import AddPage from '../add-video-page/add-video';
import VideoList from '../contexts/contextCheck';
import { VideoProvider } from '../contexts/videoContext'; // Import the VideoContext
import { AccountProvider } from '../contexts/accountContext'; // Import the AccountContext
import { LoginProvider } from '../contexts/loginContext'; // Import the LoginContext
import { VideoDataProvider} from '../contexts/videoDataContext';
import LoginValidation from './loginValidation'; // Import the LoginValidation component
 
const AppRoutes = () => {
  return (
    <VideoDataProvider>
    <LoginProvider>
    <AccountProvider>
    <VideoProvider>
    <Routes>
      <Route path="/" element={<MainPage />} />
      <Route path="/registration" element={<Registration />} />
      <Route path="/login" element={<Login />} />
      <Route path="/mainPage" element={<MainPage />} />
      <Route path="/video" element={<VideoWatchPage initVideoUrl={'/db/videos/1Digitalization; Where to start_.mp4'} />} />
      <Route path="/uploadPage" element={<LoginValidation><UploadPage /></LoginValidation>} />
      <Route path="/addVideo" element={<LoginValidation><AddPage /></LoginValidation>} />
      <Route path="/videoList" element={<VideoList />} />
    </Routes>
    </VideoProvider>
    </AccountProvider>
    </LoginProvider>
    </VideoDataProvider>
  );
};

export default AppRoutes;
