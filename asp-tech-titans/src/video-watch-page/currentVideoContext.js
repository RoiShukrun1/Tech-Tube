import React, { createContext, useState } from 'react';

// Create a context
export const CurrentVideoContext = createContext();

// Provider component
export const CurrentVideoProvider = ({ children }) => {
  const [videoUrl, setVideoUrl] = useState('/db/videos/1Digitalization; Where to start_.mp4');

  return (
    <CurrentVideoContext.Provider value={{ videoUrl, setVideoUrl }}>
      {children}
    </CurrentVideoContext.Provider>
  );
};