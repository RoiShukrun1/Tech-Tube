import "bootstrap/dist/css/bootstrap.min.css";
import "./publisherChannelPage.css";
import React, { useState, useEffect, useContext } from "react";
import VideoThumbnail from "../main-page/components/video-thumbnail/videoThumbnail";
import Sidebar from "../main-page/components/side-bar/sideBar";
import Header from "../main-page/components/header/header";
import { CurrentVideoContext } from "../video-watch-page/currentVideoContext";
import { ThemeContext } from "../contexts/themeContext";
import { VideoDataContext } from "../contexts/videoDataContext";
import { LoginContext } from "../contexts/loginContext";
import { UserContext } from "../contexts/userContext";
import PublisherInfo from "./components/publisherInfo/publisherInfo";
import { CurrentPublisherContext } from "./currentPublisherContext";

import publisherBanner from "../db/8.jpg";
import publisherImage from "../db/techTitansLogo.png";
import axios from "axios";

function getObjectByUrl(jsonData, username) {
  return jsonData.find((obj) => obj.username === username);
}

const PublisherChannelPage = () => {
  const { setVideoUrl } = useContext(CurrentVideoContext);
  const { darkMode } = useContext(ThemeContext);
  const { videoData, setVideoData } = useContext(VideoDataContext);
  const [videos, setVideos] = useState(videoData);
  const { login } = useContext(LoginContext);
  const { setUsers } = useContext(UserContext);
  const { publisher } = useContext(CurrentPublisherContext);
  const [publisherData, setPublisherData] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      await updatePublisherData();
      await updateVideosData();
    };
    fetchData();
  }, []);

  const getUser = async (publisher) => {
    const path = "http://localhost/api/users/" + publisher;
    const response = await fetch(path);
    const user = await response.json();
    // const response = await axios.get(path);
    return user;
  };

  const getPublisherVideos = async (publisher) => {
    const path = "http://localhost/api/users/" + publisher +  "/videos";
    const response = await fetch(path);
    const videos = await response.json();
    console.log(videos);
    return videos;
}

const updateVideosData = async () => {
  const vids = await getPublisherVideos(publisher);
  setVideos(vids);
}
  
  const updatePublisherData = async () => {
    const usr = await getUser(publisher);
    if(usr) {
    const publisherData = {
      nickname: usr.nickname,
      username: usr.username,
      subscribers: "11",
      videos: videos.length,
      banner: publisherBanner,
      image: usr.image,
      currentUser: login,
      setUsers: setUsers,
    };

    setPublisherData(publisherData);

  }
  };


  useEffect(() => {
    document.body.style.overflowX = "hidden";
    return () => {
      document.body.style.overflowX = "auto";
    };
  }, []);

  useEffect(() => {
    setVideos(videoData);
  }, [videoData]);


  // Function to handle search and filter videos
  const handleSearch = (query) => {
    const filteredVideos = videoData.filter((video) =>
      video.title.toLowerCase().includes(query.toLowerCase())
    );
    if (filteredVideos.length === 0) {
      return;
    }
    setVideoUrl(filteredVideos[0].videoUploaded);
  };

  return (
    <div className={`container-fluid main-page ${darkMode ? "dark" : ""}`}>
      <div className="row no-gutters">
        <div className="col-md-2 p-0">
          <Sidebar />
        </div>
        <div className="col-md-10 p-0">
          <Header onSearch={handleSearch} />
          <div>
            {publisherData ? (
              <PublisherInfo {...publisherData} />
            ) : (
              <p>Loading...</p>
            )}
          </div>
          <div className="container-fluid p-0">
            <div className="row no-gutters">
              {videos.map((newVideo, index) => (
                <div key={index} className="col-md-4 p-1">
                  <VideoThumbnail video={newVideo}/>
                </div>
              ))}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default PublisherChannelPage;
