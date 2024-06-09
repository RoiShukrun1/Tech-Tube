import React from 'react';
import { Routes, Route } from 'react-router-dom';
import MainPage from '../main-page/mainPage';
import Login from '../login-page/login';
import Registration from '../registration-page/registration';
import VideoWatchPage from '../video-watch-page/video-watch-page';
import UploadPage from '../add-video-page/upload-video';
import AddPage from '../add-video-page/add-video';
const AppRoutes = () => {
  return (
    <Routes>
      <Route path="/" element={<MainPage />} />
      <Route path="/registration" element={<Registration />} />
      <Route path="/login" element={<Login />} />
      <Route path="/mainPage" element={<MainPage />} />
      <Route path="/video" element={<VideoWatchPage />} />
      <Route path="/uploadPage" element={<UploadPage />} />
      <Route path="/addVideo" element={<AddPage />} />
    </Routes>
  );
};

export default AppRoutes;
