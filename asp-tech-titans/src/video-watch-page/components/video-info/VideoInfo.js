import { useState } from 'react';
import './VideoInfo.css';

function VideoInfo({ videoTitle, views, date, publisherImg, publisher, info }) {

    const [susbscribeButtonIsVisible, setSusbscribeButtonIsVisible] = useState(true);

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
