import React, { useState, useContext, useEffect } from 'react';
import './videoThumbnail.css';
import { Link } from 'react-router-dom';
import { LoginContext } from '../../../contexts/loginContext';
import { VideoDataContext } from '../../../contexts/videoDataContext';
import { incrementViews } from '../../../video-watch-page/components/related-videos/videoCard';
import { CurrentVideoContext } from '../../../video-watch-page/currentVideoContext';
import { UserContext } from '../../../contexts/userContext';
import { CurrentPublisherContext } from '../../../publisher-chanel-page/currentPublisherContext';
import axios from 'axios';

function getObjectByUrl(jsonData, url) {
  return jsonData.find(obj => obj.videoUploaded === url);
}

const VideoThumbnail = ({ video, onDelete }) => {
  const [isHovered, setIsHovered] = useState(false);
  const { login } = useContext(LoginContext);
  const { videoData, deleteVideo, setVideoData } = useContext(VideoDataContext);
  const { setVideoUrl } = useContext(CurrentVideoContext);
  const { setPublisher } = useContext(CurrentPublisherContext);
  const serverBaseUrltype1 = 'http://localhost:80/';
  const serverBaseUrltype2 = 'http://localhost:80';
  
  const [loggedInuser, setLoggedInUser] = useState(null);
  
  useEffect(() => {
    const checkAuth = async () => {
      try {
        const response = await axios.get('http://localhost/api/token/checkAuth', { withCredentials: true });
        if (response.data.isAuthenticated) {
          setLoggedInUser(response.data.user);
        }
      } catch (error) {
        alert("Error checking authentication.");
      }
    };
    
    checkAuth();
  }, []);

  const handleMouseEnter = () => {
    if(isHovered) return;
    setIsHovered(true);
  };

  const handleMouseLeave = () => {
    setIsHovered(false);
  };

  const handleDeleteClick = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.delete(`http://localhost:80/api/users/${loggedInuser.id}/videos/${video.id}`);
      if (response.status === 200) {
        alert('Video deleted successfully');
        onDelete(video.id);
      }
    } catch (error) {
      alert('Error deleting video');
    }
  };

  const handleVideoClick = (videoUrl) => {
    setVideoUrl(videoUrl);
    incrementViews(setVideoData, getObjectByUrl(videoData, videoUrl));
  };

  const handlePublisherClick = (publisher) => {
    setPublisher(publisher);
  };

  return (
    <div
      className="video-thumbnail"
      onMouseEnter={handleMouseEnter}
      onMouseLeave={handleMouseLeave}
    >
      <Link to="/video" className='thumbnail-image'>
        <img src={serverBaseUrltype1+video.thumbnail} alt={video.title} className="thumbnail-image" onClick={() => handleVideoClick(video.videoUploaded)} />
      </Link>

      <div className="video-info">
        <div className="video-header">
          <Link to="/publisherChannel">
            <img src={serverBaseUrltype2+video.publisherImage} alt={video.publisher} className="publisher-image" onClick={() =>handlePublisherClick(video.publisher)}/>
          </Link>
          <Link to="/video">
            <h3 className="video-title" onClick={() => handleVideoClick(video.videoUploaded)}>{video.title}</h3>
          </Link>
          {loggedInuser && (video.publisher === loggedInuser.username)  && (
            <button className="delete-button" onClick={handleDeleteClick}>
              &#x1F5D1;
            </button>
          )}
        </div>
        <div className="video-details">
          <p className="video-views">{video.views} views • <span className="video-date">{video.date}</span></p>
          <Link to="/publisherChannel">
            <p className="video-publisher" onClick={() =>handlePublisherClick(video.publisher)}>{video.publisher}</p>
          </Link>
        </div>
      </div>
    </div>
  );
};

export default VideoThumbnail;
