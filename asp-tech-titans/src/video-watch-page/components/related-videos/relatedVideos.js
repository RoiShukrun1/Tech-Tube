import React from "react";
import VideoCard from "./videoCard";
import "./relatedVideos.css";

function getVideoById(jsonData, id) {
    return jsonData.find(obj => obj.id === id);
}

function relatedVideos( {videos, relatedVideos, setUrl, setVideos, setMoreInfoPressed, setInputValue} ) {
    return (
        <div className="related-videos">
            <div className="video-list">
                {relatedVideos.map((relateVideo) => (
                    <VideoCard
                        video={getVideoById(videos, relateVideo.id)}
                        setUrl={setUrl}
                        setVideos={setVideos}
                        setMoreInfoPressed={setMoreInfoPressed}
                        setInputValue={setInputValue}
                        key={getVideoById(videos, relateVideo.id).id}
                    />
                ))}
            </div>
        </div>
    );
}

export default relatedVideos;
export { getVideoById };