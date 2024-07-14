import React from 'react';
import './publisherInfo.css';
import SubscribeButton from './subscribeButton';
import { useContext } from 'react';
import { CurrentPublisherContext } from '../../../publisher-chanel-page/currentPublisherContext';
import { Link } from 'react-router-dom';

// Function to display publisher information
function PublisherInfo({ publisherImage, publisherName, currentUser }) {

    const { setPublisher } = useContext(CurrentPublisherContext);

    /**
   * Handle publisher click event
   * Redirects to the publisher channel page.
   */
    const handlePublisherClick = (publisher) => {
        setPublisher(publisher);
        localStorage.setItem('publisher', publisher);
    };

    const baseServerUrl = 'http://localhost';

    return (
        <span>
            <Link to="/publisherChannel">
                <img className="publisher-photo" src={baseServerUrl + publisherImage} alt={publisherName}
                    onClick={() => handlePublisherClick(publisherName)} />
            </Link>
            <h1 className='publisher'>{publisherName}</h1>
            <SubscribeButton
                currentUser={currentUser} publisher={publisherName} />
        </span>
    );
}

export default PublisherInfo;
