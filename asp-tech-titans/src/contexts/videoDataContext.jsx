import React, { createContext, useState } from 'react';
import jsonData from '../db/videos.json';


export const VideoDataContext = createContext();

export const VideoDataProvider = ({ children }) => {
  const [videoData, setVideoData] = useState(jsonData);

  const addVideoData = (newVideoData) => {
    setVideoData((prevVideoData) => [...prevVideoData, newVideoData]);
  };

  const deleteVideo = (id) => {
    setVideoData(videoData.filter(video => video.id !== id));
  };

  return (
    <VideoDataContext.Provider value={{ videoData, addVideoData, deleteVideo, setVideoData }}>
      {children}
    </VideoDataContext.Provider>
  );
};
