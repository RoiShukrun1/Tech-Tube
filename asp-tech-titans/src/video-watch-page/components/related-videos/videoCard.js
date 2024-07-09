import React from 'react';
import './videoCard.css';
import { useState } from 'react';
import axios from 'axios';
import { useContext } from 'react';
import { VideoDataContext } from '../../../contexts/videoDataContext';
import { CurrentVideoContext } from '../../currentVideoContext';

// Function to increment views
export const incrementViews = async (setVideos, video) => {
    
    setVideos(prevVideos => {
        const updatedVideos = [...prevVideos];

        var thisVideoTitle = video.title;
        const index = updatedVideos.findIndex(video => video.title === thisVideoTitle);

        const updatedVideo = { ...updatedVideos[index] };

        updatedVideo.views++;

        updatedVideos[index] = updatedVideo;

        return updatedVideos;
    });

    let updatedViewsNumber = video.views + 1;
    await axios.patch('http://localhost/api/users/id/videos/' + video.id, { views: updatedViewsNumber });
};

// Video card component
function VideoCard({ video, setMoreInfoPressed, setInputValue }) {

    const [isHovered, setIsHovered] = useState(false);
    const { setVideoData } = useContext(VideoDataContext);
    const { setVideoUrl } = useContext(CurrentVideoContext);

    const baseServerUrl = 'http://localhost/';

    // Functions to handle mouse enter, mouse leave and click
    const handleMouseEnter = () => {
        setIsHovered(!isHovered);
    };

    // Function to handle mouse leave
    const handleMouseLeave = () => {
        setIsHovered(!isHovered);
    };

    // Function to handle click
    const handleClick = async () => {
        setVideoUrl(video.videoUploaded);
        incrementViews(setVideoData, video);
        setMoreInfoPressed(false);
        setInputValue('');
        localStorage.setItem('videoURL', video.videoUploaded)
    };

    // Function to render play icon
    const renderPlayIcon = () => {
        return (
            <div className="play-icon">
                <svg onClick={handleClick}
                    xmlns="http://www.w3.org/2000/svg"
                    width="32" height="32" fill="currentColor" className="bi bi-play-fill" viewBox="0 0 16 16">
                    <path d="m11.596 8.697-6.363 3.692c-.54.313-1.233-.066-1.233-.697V4.308c0-.63.692-1.01 1.233-.696l6.363 3.692a.802.802 0 0 1 0 1.393" />
                </svg>
            </div>
        );
    }

    return (
        <div className="card">
            <div className="row g-0">
                <div className="col"
                    onMouseEnter={handleMouseEnter}
                    onMouseLeave={handleMouseLeave} >
                    <img src={baseServerUrl + video.thumbnail} alt=""
                        className="img-fluid rounded-start videoCard-image"
                        onClick={handleClick}
                    />
                    {isHovered && renderPlayIcon()}
                </div>
                <div className="col">
                    <div className="card-body">
                        <h5 className="card-title">{video.title}</h5>
                        <p className="card-text">{video.publisher}</p>
                        <p className="card-text"><small className="views">{video.views} • {video.date}</small></p>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default VideoCard;