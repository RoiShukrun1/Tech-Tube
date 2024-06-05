import React, { useEffect } from 'react';
import './videoInfo.css';
import PublisherInfo from './publisherInfo';
import SubscribeButton from './subscribeButton';

function VideoInfo({ currentVideo, currentUser, setUsers, setMoreInfoPressed, moreInfoPressed }) {
    const { videoTitle, views, date, publisherImg, publisher, info } = currentVideo;

    // Generate a unique ID for each instance of the component
    const collapseId = `collapse-${videoTitle.replace(/\s+/g, '-')}`;

    // Reset collapse state when currentVideo or setMoreInfoPressed changes
    useEffect(() => {
        setMoreInfoPressed(false);
    }, [currentVideo, setMoreInfoPressed]);

    return (
        <div>
            <h1 className="title">{videoTitle}</h1>
            <PublisherInfo publisherImg={publisherImg} publisher={publisher}
                setUsers={setUsers} currentUser={currentUser} />
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
                        {info}
                    </div>
                </div>
            </div>
        </div>
    );
}

export default VideoInfo;
