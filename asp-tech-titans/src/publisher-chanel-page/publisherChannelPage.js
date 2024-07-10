import "bootstrap/dist/css/bootstrap.min.css";
import "./publisherChannelPage.css";
import React, { useState, useEffect, useContext } from "react";
import axios from "axios";
import VideoThumbnail from "../main-page/components/video-thumbnail/videoThumbnail";
import Sidebar from "../main-page/components/side-bar/sideBar";
import Header from "../main-page/components/header/header";
import { ThemeContext } from "../contexts/themeContext";
import { VideoDataContext } from "../contexts/videoDataContext";
import { UserContext } from "../contexts/userContext";
import PublisherInfo from "./components/publisherInfo/publisherInfo";
import noImage from "../images/noImage.svg";
import { useLocation } from "react-router-dom";

const PublisherChannelPage = () => {
  const { darkMode } = useContext(ThemeContext);
  const { videoData } = useContext(VideoDataContext);
  const { setUsers } = useContext(UserContext);
  const [publisher, setPublisher] = useState(null);
  const [publisherData, setPublisherData] = useState(null);
  const [videos, setVideos] = useState([]);
  const [login, setLogin] = useState(null);
  const location = useLocation();
  const serverBaseUrl = 'http://localhost:80';

  // Check authentication and set login state
  useEffect(() => {
    const checkAuth = async () => {
      try {
        const response = await axios.get('http://localhost/api/token/checkAuth', { withCredentials: true });
        if (response.data.isAuthenticated) {
          setLogin(response.data.user);
        }
      } catch (error) {
        alert("Error checking authentication.");
      }
    };

    checkAuth();
  }, []);

  // Retrieve publisher from local storage and set it in state
  useEffect(() => {
    const storedPublisher = localStorage.getItem("publisher");
    if (storedPublisher) {
      setPublisher(storedPublisher);
    }
  }, [location.state]);

  // Fetch publisher data only after publisher and login are set
  useEffect(() => {
    if (publisher) {
      const fetchData = async () => {
        const fetchedVideos = await getPublisherVideos(publisher);
        setVideos(fetchedVideos);
        await updatePublisherData(fetchedVideos);
      };
      fetchData();
    }
  }, [publisher, login]);

  const getUser = async (publisher) => {
    const path = "http://localhost/api/users/" + publisher;
    const response = await fetch(path);
    const user = await response.json();
    return user;
  };

  const getPublisherVideos = async (publisher) => {
    const path = "http://localhost/api/users/" + publisher + "/videos";
    const response = await fetch(path);
    const videos = await response.json();
    return videos;
  };

  const getPublisherSubs = async (publisher) => {
    const path = "http://localhost/api/users/" + publisher + "/subscribers";
    const response = await fetch(path);
    const subscribers = await response.json();
    return subscribers;
  };

  const updatePublisherData = async (fetchedVideos) => {
    const usr = await getUser(publisher);
    const subs = await getPublisherSubs(publisher);
    const isPublisher = login && login.username === publisher;

    if (!usr) {
      const publisherData = {
        nickname: "Not Available",
        username: "Not Available",
        subscribers: "0",
        videos: fetchedVideos.length,
        banner: noImage,
        image: noImage,
        setUsers: setUsers,
        isPublisher: isPublisher,
      };
      setPublisherData(publisherData);
    } else {
      const imageurl = serverBaseUrl + usr.image;
      const publisherData = {
        nickname: usr.nickname,
        username: usr.username,
        subscribers: subs.length,
        videos: fetchedVideos.length,
        banner: usr.banner,
        image: imageurl,
        setUsers: setUsers,
        isPublisher: isPublisher,
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
    if (videoData && publisher) {
      const filteredVideos = videoData.filter(video => video.publisher === publisher);
      setVideos(filteredVideos);
    }
  }, [videoData, location.state, login, publisher]);


  // Function to handle video deletion
  const deleteVideo = (videoId) => {
    setVideos((prevVideos) => prevVideos.filter(video => video.id !== videoId));
  };

  if (!publisher) {
    return <div>Loading...</div>;
  }

  return (
    <div className={`container-fluid main-page ${darkMode ? "dark" : ""}`}>
      <div className="row no-gutters">
        <div className="col-md-2 p-0">
          <Sidebar />
        </div>
        <div className="col-md-10 p-0">
          <Header />
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
                  <VideoThumbnail video={newVideo} onDelete={deleteVideo} />
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
