import React, { useState, useContext } from 'react';
import './videoThumbnail.css';
import { Link } from 'react-router-dom';
import { LoginContext } from '../../../contexts/loginContext';
import { VideoDataContext } from '../../../contexts/videoDataContext';
import { incrementViews } from '../../../video-watch-page/components/related-videos/videoCard';
import { CurrentVideoContext } from '../../../video-watch-page/currentVideoContext';
import { UserContext } from '../../../contexts/userContext';
import { CurrentPublisherContext } from '../../../publisher-chanel-page/currentPublisherContext';




function getObjectByUrl(jsonData, url) {
  return jsonData.find(obj => obj.videoUploaded === url);
}

/**
 * VideoThumbnail Component
 * 
 * This component renders a thumbnail for a video, displaying video details and a delete button if the logged-in user is the publisher.
 * 
 * Props:
 * - video (object): The video data object containing details like id, title, thumbnail, publisher, etc.
 * - onClick (function): A callback function to handle click events on the thumbnail.
 */
const VideoThumbnail = ({ video }) => {
  const [isHovered, setIsHovered] = useState(false); // State to manage hover status
  const { login } = useContext(LoginContext); // Retrieve the login context
  const { videoData, deleteVideo, setVideoData } = useContext(VideoDataContext); // Retrieve the deleteVideo function from video data context
  const { setVideoUrl } = useContext(CurrentVideoContext);
  const { users } = useContext(UserContext);
  const { setPublisher } = useContext(CurrentPublisherContext);

  /**
   * Handle mouse enter event
   * Sets the hover state to true.
   */
  const handleMouseEnter = () => {
    if(isHovered) return;
    setIsHovered(true);
  };

  /**
   * Handle mouse leave event
   * Sets the hover state to false.
   */
  const handleMouseLeave = () => {
    setIsHovered(false);
  };
  /**
   * Handle delete button click event
   * Prevents default action and deletes the video.
   */
  const handleDeleteClick = (e) => {
    e.preventDefault();
    deleteVideo(video.id);
  };

  /**
   * Handle video click event
   * Redirects to the video page.
   */
  const handleVideoClick = (videoUrl) => {
    setVideoUrl(videoUrl);
    incrementViews(setVideoData, getObjectByUrl(videoData, videoUrl));
  };

  /**
   * Handle publisher click event
   * Redirects to the publisher channel page.
   */
  const handlePublisherClick = (publisher) => {
    setPublisher(publisher);
  };


  return (
      <div
        className="video-thumbnail"
        onMouseEnter={handleMouseEnter}
        onMouseLeave={handleMouseLeave}
      >
        <Link to="/video">
          <img src={video.thumbnail} alt={video.title} className="thumbnail-image" onClick={() => handleVideoClick(video.videoUploaded)} />
        </Link>

        <div className="video-info">
          <div className="video-header">
            <Link to="/publisherChannel">
            <img src={video.publisherImage} alt={video.publisher} className="publisher-image" onClick={handlePublisherClick(video.publisher)}/>
            </Link>
            <h3 className="video-title">{video.title}</h3>
            {login && login.username === video.publisher && (
              <button className="delete-button" onClick={handleDeleteClick}>
                &#x1F5D1;
              </button>
            )}
          </div>
          <div className="video-details">
            <p className="video-views">{video.views} views • <span className="video-date">{video.date}</span></p>
            <Link to="/publisherChannel">
              <p className="video-publisher">{video.publisher}</p>
            </Link>
          </div>
        </div>
      </div>
  );
};

export default VideoThumbnail;
