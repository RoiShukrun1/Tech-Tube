import React from "react";
import VideoCard from "./videoCard";
import "./relatedVideos.css";
import jsonData from "../../../db/videos.json";

function getVideoById(jsonData, id) {
    return jsonData.find(obj => obj.id === id);
}

function relatedVideos( {relatedVideos, setUrl, setVideos} ) {
    return (
        <div className="related-videos">
            <div className="video-list">
                {relatedVideos.map((relateVideo) => (
                    <VideoCard
                        video={getVideoById(jsonData, relateVideo.id)}
                        setUrl={setUrl}
                        setVideos={setVideos}
                        key={getVideoById(jsonData, relateVideo.id).id}
                    />
                ))}
            </div>
        </div>
    );
}

export default relatedVideos;