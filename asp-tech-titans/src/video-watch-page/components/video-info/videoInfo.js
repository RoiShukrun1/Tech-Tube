import React, { useEffect } from 'react';
import './videoInfo.css';
import PublisherInfo from './publisherInfo';
import { ReactComponent as Download } from './download.svg';
import { ReactComponent as Share } from './share.svg';
import { useContext } from 'react';
import { ReactComponent as Pencil } from './pencil.svg';
import { VideoDataContext } from '../../../contexts/videoDataContext';
import { useState } from 'react';
import { ReactComponent as Like } from '../comments/like.svg';
import { ReactComponent as Dislike } from '../comments/dislike.svg';
import { ReactComponent as LikeSelected } from '../comments/like-selected.svg';
import { ReactComponent as DislikeSelected } from '../comments/dislike-selected.svg';

// Function to copy the current URL to the clipboard
function copyUrl() {
    const url = window.location.href;
    navigator.clipboard.writeText(url);
}

function VideoInfo({ currentVideo, currentUser, setUsers, setMoreInfoPressed, moreInfoPressed }) {
    // State to manage pencil button click
    const [isPencilClicked, setIsPencilClicked] = useState(false);
    // State for video title input
    const [VideoTitleInputValue, setVideoTitleInputValue] = useState(currentVideo.title);
    // State for description input
    const [descriptionInputValue, setDescriptionInputValue] = useState(currentVideo.description);
    // Context for setting video data
    const { setVideoData } = useContext(VideoDataContext); 

    // Handle changes in video title input
    const handleVideoTitleInputChange = (event) => {
        setVideoTitleInputValue(event.target.value);
    };

    // Handle changes in description input
    const handleDescriptionInputValueChange = (event) => {
        setDescriptionInputValue(event.target.value);
    };

    // Handle 'Enter' key press to close input fields
    const handleKeyDown = (event) => {
        if (event.key === 'Enter') {
            closeInput();
        }
    };

    // Set new video title in video data context
    const setNewVideoTitle = (newTitle) => {
        setVideoData(prevVideos => {
            const updatedVideos = [...prevVideos];
            const thisVideo = updatedVideos.find(video => video.id === currentVideo.id);
            if (thisVideo) {
                thisVideo.title = newTitle;
            }
            return updatedVideos;
        });
    };

    // Set new description in video data context
    const setNewDescription = (NewDescription) => {
        setVideoData(prevVideos => {
            const updatedVideos = [...prevVideos];
            const thisVideo = updatedVideos.find(video => video.id === currentVideo.id);
            if (thisVideo) {
                thisVideo.description = NewDescription;
            }
            return updatedVideos;
        });
    };

    // Close input fields and save changes
    const closeInput = () => {
        setNewVideoTitle(VideoTitleInputValue);
        setNewDescription(descriptionInputValue);
        setIsPencilClicked(false);
    };

    // Check if the current user is the owner of the video
    const currentUserIsOwnerOfVideo = () => {
        return currentUser && currentUser.username === currentVideo.publisher;
    };

    // Handle video download
    const download = () => {
        const downloadUrl = currentVideo.videoUploaded;
        const link = document.createElement('a');
        link.href = downloadUrl;
        link.setAttribute('download', '');
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
    };

    // Handle like button press
    const likePressed = () => {
        if (!currentUser) {
            alert('You must login to press like!');
            return;
        }
        setVideoData(prevVideos => {
            const updatedVideos = prevVideos.map(video => {
                if (video.id === currentVideo.id) {
                    const index = video.usersLikes.findIndex(item => item.username === currentUser.username);
                    if (index === -1) {
                        video.usersLikes.push({ username: currentUser.username });
                    } else {
                        video.usersLikes.splice(index, 1);
                    }
                    video.usersUnlikes = video.usersUnlikes.filter(item => item.username !== currentUser.username);
                }
                return video;
            });
            return updatedVideos;
        });
    };

    // Handle dislike button press
    const unlikePressed = () => {
        if (!currentUser) {
            alert('You must login to press dislike!');
            return;
        }
        setVideoData(prevVideos => {
            const updatedVideos = prevVideos.map(video => {
                if (video.id === currentVideo.id) {
                    const index = video.usersUnlikes.findIndex(item => item.username === currentUser.username);
                    if (index === -1) {
                        video.usersUnlikes.push({ username: currentUser.username });
                    } else {
                        video.usersUnlikes.splice(index, 1);
                    }
                    video.usersLikes = video.usersLikes.filter(item => item.username !== currentUser.username);
                }
                return video;
            });
            return updatedVideos;
        });
    };

    // Destructure properties from the current video
    var { title, views, date, publisherImage, publisher, description } = currentVideo;

    // Generate a unique ID for the collapse component
    const collapseId = `collapse-${title.replace(/\s+/g, '-')}`;

    // Reset collapse state when currentVideo or setMoreInfoPressed changes
    useEffect(() => {
        setMoreInfoPressed(false);
    }, [currentVideo, setMoreInfoPressed]);

    // Check if the current user has liked the video
    const userLike = () => {
        if (!currentUser || !currentVideo.usersLikes) return false;
        return currentVideo.usersLikes.some(user => user.username === currentUser.username);
    };

    // Check if the current user has disliked the video
    const userUnLike = () => {
        if (!currentUser || !currentVideo.usersUnlikes) return false;
        return currentVideo.usersUnlikes.some(user => user.username === currentUser.username);
    };

    var isLiked = userLike();
    var isUnLiked = userUnLike();

    return (
        <div className='video-info-section'>
            <h1 className="title">
                {isPencilClicked ? (
                    <input
                        onChange={handleVideoTitleInputChange}
                        placeholder='Title...'
                        onKeyDown={handleKeyDown}
                        value={VideoTitleInputValue}
                        style={{ 'background-color': 'var(--background-color)', 'color': 'var(--text-color)', 'border': 'none' }}
                    />
                ) : (
                    title
                )}
                {currentUser && currentUserIsOwnerOfVideo() && (
                    <button type="button" className='pencil-button' onClick={() => setIsPencilClicked(true)}>
                        <Pencil />
                    </button>
                )}
            </h1>
            <PublisherInfo
                publisherImage={publisherImage}
                publisherName={publisher}
                setUsers={setUsers}
                currentUser={currentUser}
            />
            <button type="button" className="btn btn-light download-button" onClick={download}>
                <Download /> Download
            </button>
            <button
                type="button"
                className="btn btn-light share-button dropdown-toggle"
                data-bs-toggle="dropdown"
                aria-expanded="false"
            >
                <Share /> Share
            </button>
            <div className="btn-group" role="group" style={{ float: 'right' }}>
                <ul className="dropdown-menu">
                    <li>
                        <button className="dropdown-item" href="#" onClick={copyUrl}>
                            Copy Url
                        </button>
                    </li>
                </ul>
            </div>
            <span>
                <button
                    onClick={() => likePressed()}
                    type="button"
                    className={"btn btn-outline-secondary"}
                    style={{ marginLeft: '1%' }}
                >
                    {isLiked ? <LikeSelected className='icons' style={{ margin: 0 }} /> : <Like className='icons' style={{ margin: 0 }} />}
                </button>
                <h3 className="likesNumber" style={{ margin: '1%' }}>{currentVideo.usersLikes.length}</h3>
                <button onClick={() => unlikePressed()} type="button" className={"btn btn-outline-secondary"}>
                    {isUnLiked ? <DislikeSelected className='icons' style={{ margin: 0 }} /> : <Dislike className='icons' style={{ margin: 0 }} />}
                </button>
            </span>
            <div className="alert alert-secondary" role="alert">
                <h2 className='views'>Views: {views}</h2>
                <h2 className='date'>Date: {date}</h2>
                <button
                    className="btn"
                    type="button"
                    data-bs-toggle="collapse"
                    data-bs-target={`#${collapseId}`}
                    aria-expanded={moreInfoPressed ? "true" : "false"}
                    aria-controls={collapseId}
                    onClick={() => {
                        setMoreInfoPressed(!moreInfoPressed);
                    }}
                >
                    {moreInfoPressed ? "...less" : "...more"}
                </button>
                <div className={`collapse ${moreInfoPressed ? 'show' : ''}`} id={collapseId}>
                    <div className="more-info">
                        <div>
                            {isPencilClicked ? (
                                <input
                                    className='inputVideoTitle'
                                    placeholder='Description...'
                                    onChange={handleDescriptionInputValueChange}
                                    onKeyDown={handleKeyDown}
                                    value={descriptionInputValue}
                                />
                            ) : (
                                description
                            )}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default VideoInfo;
