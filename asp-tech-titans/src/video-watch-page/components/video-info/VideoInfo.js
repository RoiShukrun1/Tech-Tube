import React, { useEffect } from 'react';
import './videoInfo.css';
import PublisherInfo from './PublisherInfo';
import SubscribeButton from './SubscribeButton';
import { ReactComponent as Download } from './download.svg';
import { ReactComponent as Share } from './share.svg';

function copyUrl() {
    const url = window.location.href;
    navigator.clipboard.writeText(url);
}

function VideoInfo({ currentVideo, currentUser, setUsers, setMoreInfoPressed, moreInfoPressed }) {

    const download = () => {
        const downloadUrl = currentVideo.videoUrl;
        const link = document.createElement('a');
        link.href = downloadUrl;
        link.setAttribute('download', '');
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
    }

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
            <button type="button" className="btn btn-light download-button"
                onClick={download}><Download /> Download
            </button>

            <div className="btn-group" role="group" style={{float: 'right'}}>
                <button type="button"
                    className="btn btn-light share-button dropdown-toggle"
                    data-bs-toggle="dropdown" 
                    aria-expanded="false"><Share /> Share
                </button>
                <ul className="dropdown-menu">
                    <li><button className="dropdown-item" href="#" onClick={copyUrl}>Copy Url</button></li>
                </ul>
            </div>

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
