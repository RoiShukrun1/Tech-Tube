import React, { createContext, useState, useEffect } from 'react';
// import jsonData from '../db/videos.json';
import axios from 'axios';

export const VideoDataContext = createContext();

export const VideoDataProvider = ({ children }) => {
  const [videoData, setVideoData] = useState([]);

  useEffect(() => {
    const fetchVideoList = async () => {
      try {
        const response = await axios.get('http://localhost/api/videos/all');
        setVideoData(response.data);
      } catch (error) {
        console.error('Error fetching video list:', error);
      }
    };

    fetchVideoList();
  }, []);

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
