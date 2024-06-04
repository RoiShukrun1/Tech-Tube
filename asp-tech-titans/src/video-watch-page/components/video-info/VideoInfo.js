import './VideoInfo.css';

function checkIfUserIsSubscribed(currentUser, publisher) {
    return currentUser.subscriptions.includes(publisher);
}

function VideoInfo({ currentVideo, currentUser, setUsers }) {

    var susbscribeButtonIsVisible = !checkIfUserIsSubscribed(currentUser, currentVideo.publisher);

    const removeSubscription = () => {
        setUsers(prevUsers => {
            // Create a copy of the users array to avoid mutating state directly
            const updatedUsers = [...prevUsers];

            if (currentUser && currentUser.subscriptions) {
                currentUser.subscriptions = 
                currentUser.subscriptions.filter(sub => sub !== currentVideo.publisher);
            }

            return updatedUsers;
        });
    };

    const addSubscription = () => {
        setUsers(prevUsers => {
            // Create a copy of the users array to avoid mutating state directly
            const updatedUsers = [...prevUsers];

            if (currentUser && currentUser.subscriptions) {
                currentUser.subscriptions.push(currentVideo.publisher);
            }

            return updatedUsers;
        });
    };

    var videoTitle = currentVideo.videoTitle, views = currentVideo.views, date = currentVideo.date, 
    publisherImg = currentVideo.publisherImg, publisher = currentVideo.publisher, info = currentVideo.info;

    const swtichButtons = () => {
        if (currentUser.subscriptions.includes(publisher)) {
            removeSubscription()
        } else {
            addSubscription()
        }
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
