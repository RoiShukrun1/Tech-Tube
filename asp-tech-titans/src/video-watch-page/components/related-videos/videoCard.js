import React from 'react';
import './videoCard.css';

function videoCard({ video }) {
    return (
        <div className="video-card">
            <div className="card mb-3">
                <div className="row g-0">
                    <div className="col-md-6">
                        <img src={video.imgUrl} className="img-fluid rounded-start"></img>
                    </div>
                    <div className="col-md-6">
                        <div className="card-body">
                            <h5 className="card-title">{video.videoTitle}</h5>
                            <p className="card-text">{video.publisher}</p>
                            <p className="card-text"><small class="text-body-secondary">{video.views} {video.date}</small></p>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    );
}

export default videoCard;