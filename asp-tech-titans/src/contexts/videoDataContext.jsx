import React, { createContext, useState } from 'react';

export const VideoDataContext = createContext();

export const VideoDataProvider = ({ children }) => {
  const [videoData, setVideoData] = useState([]);

  const addVideoData = (newVideoData) => {
    setVideoData((prevVideoData) => [...prevVideoData, newVideoData]);
  };

  return (
    <VideoDataContext.Provider value={{ videoData, addVideoData }}>
      {children}
    </VideoDataContext.Provider>
  );
};
