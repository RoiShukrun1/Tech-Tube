import React, { createContext, useState } from 'react';

export const VideoDataContext = createContext();

export const VideoDataProvider = ({ children }) => {
  const [videoData, setVideoData] = useState([]);

  const addVideoData = (newVideoData) => {
    setVideoData((prevVideoData) => [...prevVideoData, newVideoData]);
  };

  const deleteVideo = (id) => {
    setVideoData(videoData.filter(video => video.id !== id));
  };

  return (
    <VideoDataContext.Provider value={{ videoData, addVideoData, deleteVideo }}>
      {children}
    </VideoDataContext.Provider>
  );
};
