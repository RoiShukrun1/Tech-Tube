import React from 'react';
import './publisherInfo.css';
import SubscribeButton from '../../../video-watch-page/components/video-info/subscribeButton';

const PublisherInfo = ({ nickname, username, subscribers, videos, banner, image, currentUser, setUsers}) => {
    return (
        <div className="publisher-info-div-pcp">
            <div className="publisher-banner-pcp">
                <img className="publisher-banner-image-pcp" src={banner} alt="publisherBanner" />
            </div>
            <div className="publisher-content">
                <div className="publisher-image-div-pcp">
                    <img className="publisher-image-pcp" src={image} alt="publisherImage" />
                </div>
                <div className="publisher-info">
                    <p className='publisher-info-p1'>{username}</p>
                    <p className='publisher-info-p2'>@{nickname} • {subscribers} subscribers • {videos} videos</p>
                    <div className="sub-pcp">
                        <SubscribeButton
                            setUsers={setUsers} currentUser={currentUser} publisher={username} />
                    </div>
                </div>
            </div>
        </div>
    );
};

export default PublisherInfo;
