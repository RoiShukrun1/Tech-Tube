import React from 'react';
import './videoCard.css';
import { useState } from 'react';


function VideoCard({ video, setUrl, setVideos, setMoreInfoPressed, setInputValue }) {

    const [isHovered, setIsHovered] = useState(false);

    const handleMouseEnter = () => {
        setIsHovered(!isHovered);
    };

    const handleMouseLeave = () => {
        setIsHovered(!isHovered);
    };

    const handleClick = () => {
        setUrl(video.videoUrl);
        incrementViews();
        setMoreInfoPressed(false);
        setInputValue('');
    };

    const incrementViews = () => {
        setVideos(prevVideos => {
            const updatedVideos = [...prevVideos];

            var thisVideoTitle = video.videoTitle;
            const index = updatedVideos.findIndex(video => video.videoTitle === thisVideoTitle);

            const updatedVideo = { ...updatedVideos[index] };

            updatedVideo.views++;

            updatedVideos[index] = updatedVideo;

            return updatedVideos;
        });
    };

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
                    <img src={video.imgUrl}
                        className="img-fluid rounded-start"
                        onClick={handleClick}
                    />
                    {isHovered && renderPlayIcon()}
                </div>
                <div className="col">
                    <div className="card-body">
                        <h5 className="card-title">{video.videoTitle}</h5>
                        <p className="card-text">{video.publisher}</p>
                        <p className="card-text"><small className="text-body-secondary">{video.views} • {video.date}</small></p>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default VideoCard;