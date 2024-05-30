import './videoInfo.css';

function videoInfo({ videoTitle, views, date }) {
    return (

        <div>
            <h1 className="title">{videoTitle}</h1>

            <div className="alert alert-secondary" role="alert">
                <h2 className='views'>Views: {views}</h2>
                <h2 className='date'>Date: {date}</h2>

                <button className="btn btn" type="button" data-bs-toggle="collapse" data-bs-target="#collapseExample" aria-expanded="false" aria-controls="collapseExample">
                    ...more
                </button>
                <div className="collapse" id="collapseExample">
                    <div className="card card-body">
                        creator details
                    </div>
                </div>

            </div>

        </div>
    );
}

export default videoInfo;