import React, { createContext, useState } from 'react';
import jsonData from '../db/videos.json';
import axios from 'axios';
import { useEffect } from 'react';

export const VideoDataContext = createContext();

export const VideoDataProvider = ({ children }) => {
  const [videoData, setVideoData] = useState(jsonData);

  useEffect(() => {
    const fetchVideoList = async () => {
      try {
        const response = await axios.get('http://localhost/api/videos/all');
        console.log('Video List:', response.data);
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
