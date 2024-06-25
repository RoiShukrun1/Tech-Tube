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

function copyUrl() {
    const url = window.location.href;
    navigator.clipboard.writeText(url);
}

function VideoInfo({ currentVideo, currentUser, setUsers, setMoreInfoPressed, moreInfoPressed }) {

    const [isPencilClicked, setIsPencilClicked] = useState(false);
    const [VideoTitleInputValue, setVideoTitleInputValue] = useState(currentVideo.title);

    const { videoData, setVideoData } = useContext(VideoDataContext);

    const handleVideoTitleInputChange = (event) => {
        setVideoTitleInputValue(event.target.value);
    };

    const handleKeyDown = (event) => {
        if (event.key === 'Enter') {
            closeInput();
        }
    };

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

    const closeInput = () => {
        setNewVideoTitle(VideoTitleInputValue);
        setIsPencilClicked(false);
    };

    const inputIsEmpty = () => { return VideoTitleInputValue === '' ? true : false; };

    const currentUserIsOwnerOfVideo = () => {
        return currentUser && currentUser.username === currentVideo.publisher;
    }

    const download = () => {
        const downloadUrl = currentVideo.videoUploaded;
        const link = document.createElement('a');
        link.href = downloadUrl;
        link.setAttribute('download', '');
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
    }

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

    const { title, views, date, publisherImage, publisher, description } = currentVideo;

    // Generate a unique ID for each instance of the component
    const collapseId = `collapse-${title.replace(/\s+/g, '-')}`;

    // Reset collapse state when currentVideo or setMoreInfoPressed changes
    useEffect(() => {
        setMoreInfoPressed(false);
    }, [currentVideo, setMoreInfoPressed]);

    const userLike = () => {
        if (!currentUser || !currentVideo.usersLikes) return false;
        return currentVideo.usersLikes.some(user => user.username === currentUser.username);
    }

    const userUnLike = () => {
        if (!currentUser || !currentVideo.usersUnlikes) return false;
        return currentVideo.usersUnlikes.some(user => user.username === currentUser.username);
    }

    var isLiked = userLike();
    var isUnLiked = userUnLike();

    return (
        <div className='video-info-section'>
            <h1 className="title">{isPencilClicked ? <input
                className='inputVideoTitle'
                onChange={handleVideoTitleInputChange}
                onKeyDown={handleKeyDown}
                value={VideoTitleInputValue}
            />
                : title}
                {currentUser && currentUserIsOwnerOfVideo()
                    && <button type="button" className='pencil-button' onClick={() => setIsPencilClicked(true)}>
                        <Pencil />
                    </button>}
            </h1>
            <PublisherInfo publisherImage={publisherImage} publisher={publisher}
                setUsers={setUsers} currentUser={currentUser} />
            <button type="button" className="btn btn-light download-button"
                onClick={download}><Download /> Download
            </button>

            <div className="btn-group" role="group" style={{ float: 'right' }}>
                <button type="button"
                    className="btn btn-light share-button dropdown-toggle"
                    data-bs-toggle="dropdown"
                    aria-expanded="false"><Share /> Share
                </button>
                <ul className="dropdown-menu">
                    <li><button className="dropdown-item" href="#" onClick={copyUrl}>Copy Url</button></li>
                </ul>
            </div>

            <span>

                <button onClick={() => likePressed()}
                    type="button"
                    className={"btn btn-outline-secondary"}
                    style={{ marginLeft: '1%' }}
                >
                    {isLiked ? <LikeSelected className='icon' style={{ margin: 0 }} /> :
                        <Like className='icon' style={{ margin: 0 }} />}
                </button>

                <h3 className="likesNumber">{currentVideo.usersLikes.length}</h3>

                <button onClick={() => unlikePressed()} type="button"
                    className={"btn btn-outline-secondary"}>
                    {isUnLiked ? <DislikeSelected className='icon' style={{ margin: 0 }} /> :
                        <Dislike className='icon' style={{ margin: 0 }} />}
                </button>

            </span>

            <div className="alert alert-secondary" role="alert">
                <h2 className='views'>Views: {views}</h2>
                <h2 className='date'>Date: {date}</h2>
                <button className="btn" type="button" data-bs-toggle="collapse"
                    data-bs-target={`#${collapseId}`}
                    aria-expanded={moreInfoPressed ? "true" : "false"}
                    aria-controls={collapseId}
                    onClick={() => {
                        setMoreInfoPressed(!moreInfoPressed);
                    }}>
                    {moreInfoPressed ? "...less" : "...more"}
                </button>
                <div className={`collapse ${moreInfoPressed ? 'show' : ''}`} id={collapseId}>
                    <div className="more-info">
                        {description}
                    </div>
                </div>
            </div>
        </div>
    );
}

export default VideoInfo;
