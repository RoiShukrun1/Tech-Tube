import React, { createContext, useState } from 'react';

export const VideoContext = createContext();

export const VideoProvider = ({ children }) => {
  const [videos, setVideos] = useState([]);

  const addNewVideo = (video) => {
    setVideos((prevVideos) => [...prevVideos, video]);
  };

  return (
    <VideoContext.Provider value={{ videos, addNewVideo }}>
      {children}
    </VideoContext.Provider>
  );
};
