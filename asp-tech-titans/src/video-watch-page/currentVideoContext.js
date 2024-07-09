import React, { createContext, useState } from 'react';

// Create a context
export const CurrentVideoContext = createContext();

// Provider component
export const CurrentVideoProvider = ({ children }) => {
  const [videoUrl, setVideoUrl] = useState(localStorage.getItem("videoURL"));

  return (
    <CurrentVideoContext.Provider value={{ videoUrl, setVideoUrl }}>
      {children}
    </CurrentVideoContext.Provider>
  );
};