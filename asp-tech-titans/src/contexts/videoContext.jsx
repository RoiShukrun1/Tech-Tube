import React, { createContext, useState } from 'react';

export const VideoContext = createContext();

export const VideoProvider = ({ children }) => {
  const [videoList, setVideoList] = useState([]);

  const addNewVideo = (newVideo) => {
    setVideoList((prevVideoList) => [...prevVideoList, newVideo]);
  };

  return (
    <VideoContext.Provider value={{ videoList, addNewVideo }}>
      {children}
    </VideoContext.Provider>
  );
};
