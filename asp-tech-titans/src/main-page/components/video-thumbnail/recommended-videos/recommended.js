import VideoThumbnail from '../videoThumbnail';
import jsonData from '../../../../db/videos.json';
import 'bootstrap/dist/css/bootstrap.min.css';
import React, { useState } from 'react';


function Recommended() {
    const [url, setUrl] = useState('');
        return (
        <div className="container-fluid">
            <div className="categories-label">Recommended</div>
                <div className="row">
                    {jsonData.map((video, index) => (
                    <div key={index} className="col-md-3 mb-4">
                        <VideoThumbnail video={video} onClick={() => setUrl(video.videoUrl)} />
                    </div>
                ))}
            </div>
        </div>
        )
}

export default Recommended;