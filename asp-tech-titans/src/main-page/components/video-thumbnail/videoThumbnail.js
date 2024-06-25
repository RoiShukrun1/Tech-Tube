import React, { useState, useContext } from 'react';
import './videoThumbnail.css';
import { Link } from 'react-router-dom';
import { LoginContext } from '../../../contexts/loginContext';
import { VideoDataContext } from '../../../contexts/videoDataContext';

/**
 * VideoThumbnail Component
 * 
 * This component renders a thumbnail for a video, displaying video details and a delete button if the logged-in user is the publisher.
 * 
 * Props:
 * - video (object): The video data object containing details like id, title, thumbnail, publisher, etc.
 * - onClick (function): A callback function to handle click events on the thumbnail.
 */
const VideoThumbnail = ({ video, onClick }) => {
  const [isHovered, setIsHovered] = useState(false); // State to manage hover status
  const { login } = useContext(LoginContext); // Retrieve the login context
  const { deleteVideo } = useContext(VideoDataContext); // Retrieve the deleteVideo function from video data context

  /**
   * Handle mouse enter event
   * Sets the hover state to true.
   */
  const handleMouseEnter = () => {
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

  return (
    <Link to="/video">
      <div
        className="video-thumbnail"
        onClick={onClick}
        onMouseEnter={handleMouseEnter}
        onMouseLeave={handleMouseLeave}
      >
        <img src={video.thumbnail} alt={video.title} className="thumbnail-image" />
        <div className="video-info">
          <div className="video-header">
            <img src={video.publisherImage} alt={video.publisher} className="publisher-image" />
            <h3 className="video-title">{video.title}</h3>
            {login && login.username === video.publisher && (
              <button className="delete-button" onClick={handleDeleteClick}>
                &#x1F5D1;
              </button>
            )}
          </div>
          <div className="video-details">
            <p className="video-views">{video.views} views • <span className="video-date">{video.date}</span></p>
            <p className="video-publisher">{video.publisher}</p>
          </div>
        </div>
      </div>
    </Link>
  );
};

export default VideoThumbnail;
