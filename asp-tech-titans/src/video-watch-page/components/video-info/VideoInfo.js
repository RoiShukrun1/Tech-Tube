import React from 'react';
import './videoInfo.css';
import PublisherInfo from './publisherInfo';
import SubscribeButton from './subscribeButton';

function VideoInfo({ currentVideo, currentUser, setUsers }) {
    const { videoTitle, views, date, publisherImg, publisher, info } = currentVideo;

    return (
        <div>
            <h1 className="title">{videoTitle}</h1>
            <PublisherInfo publisherImg={publisherImg} publisher={publisher} 
            setUsers={setUsers} currentUser={currentUser} />
            <div className="alert alert-secondary" role="alert">
                <h2 className='views'>Views: {views}</h2>
                <h2 className='date'>Date: {date}</h2>
                <button className="btn" type="button" data-bs-toggle="collapse" data-bs-target="#collapseExample" aria-expanded="false" aria-controls="collapseExample">
                    ...more
                </button>
                <div className="collapse" id="collapseExample">
                    <div className="more-info">
                        {info}
                    </div>
                </div>
            </div>
        </div>
    );
}

export default VideoInfo;
