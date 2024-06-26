import React from 'react';
import './publisherInfo.css';
import SubscribeButton from './subscribeButton';

// Function to display publisher information
function publisherInfo({ publisherImage, publisher, setUsers, currentUser }) {
    return (
        <span>
            <img className="publisher-photo" src={publisherImage} alt={publisher} />
            <h1 className='publisher'>{publisher}</h1>
            <SubscribeButton
                setUsers={setUsers} currentUser={currentUser} publisher={publisher} />
        </span>
    );
}

export default publisherInfo;
