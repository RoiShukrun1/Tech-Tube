import './videoInfo.css';

function videoInfo({ videoTitle, views, date }) {
    return (

        <div>
            <h1 className="title">{videoTitle}</h1>

            <div className="alert alert-secondary" role="alert">
                <h2 className='views'>Views: {views}</h2>
                <h2 className='date'>Date: {date}</h2>

                <button className="btn" type="button" data-bs-toggle="collapse" data-bs-target="#collapseExample" aria-expanded="false" aria-controls="collapseExample">
                    ...more
                </button>
                <div className="collapse" id="collapseExample">
                    <div className="more-info">
                        this is the place to put all the extra info about the video
                    </div>
                </div>

            </div>

        </div>
    );
}

export default videoInfo;