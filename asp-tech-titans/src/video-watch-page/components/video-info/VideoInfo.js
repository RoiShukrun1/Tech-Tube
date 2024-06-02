import { useState } from 'react';
import './VideoInfo.css';
import usersData from "../../../db/users.json";

function getUserObjById(usersData, id) {
    return usersData.find(obj => obj.id === id);
}

function checkIfUserIsSubscribed(currentUser, publisher) {
    return currentUser.subscriptions.includes(publisher);
}


function VideoInfo({ videoTitle, views, date, publisherImg, publisher, info }) {
    const currentUser = getUserObjById(usersData, 1); // the current user for now is the first user in the users.json file

    const [susbscribeButtonIsVisible, setSusbscribeButtonIsVisible] = useState(checkIfUserIsSubscribed(currentUser, publisher));

    const swtichButtons = () => {
        setSusbscribeButtonIsVisible(!susbscribeButtonIsVisible);
    }

    return (
        <div>
            <h1 className="title">{videoTitle}</h1>
            <span>
                <img className="publisher-photo" src={publisherImg} />
                <h1 className='publisher'>{publisher}</h1>
                {susbscribeButtonIsVisible ? (
                    <button type="button"
                    className="btn btn-dark subscribe-button"
                    onClick={swtichButtons}
                    >subscribe</button>
                ) : (
                    <button type="button"
                    className="btn btn-light unsubscribe-button"
                    onClick={swtichButtons}
                    >unsubscribe</button>
                )}
            </span>

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
