import React, { createContext, useState, useEffect } from 'react';
import axios from 'axios';

export const VideoDataContext = createContext();

export const VideoDataProvider = ({ children }) => {
  const [videoData, setVideoData] = useState([]);

  const fetchVideoList = async () => {
    try {
        const response = await axios.get('http://localhost/api/videos/all');
        setVideoData(response.data);
    } catch (error) {
        console.error('Error fetching video list:', error);
    }
  };

  useEffect(() => {
    fetchVideoList();
  }, []);

  const addVideoData = (newVideoData) => {
    setVideoData((prevVideoData) => [...prevVideoData, newVideoData]);
  };

  const deleteVideo = (id) => {
    setVideoData(videoData.filter(video => video.id !== id));
  };

  const refreshVideoData = () => {
    fetchVideoList();
  };

  return (
    <VideoDataContext.Provider value={{ videoData, addVideoData, deleteVideo, setVideoData, refreshVideoData }}>
      {children}
    </VideoDataContext.Provider>
  );
};
