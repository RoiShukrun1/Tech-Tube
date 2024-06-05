import React from 'react';
import './PublisherInfo.css';
import SubscribeButton from './SubscribeButton';

function publisherInfo({ publisherImg, publisher, setUsers, currentUser }) {
    return (
        <span>
            <img className="publisher-photo" src={publisherImg} alt={publisher} />
            <h1 className='publisher'>{publisher}</h1>
            <SubscribeButton 
            setUsers={setUsers} currentUser={currentUser} publisher={publisher} />
        </span>
    );
}

export default publisherInfo;
