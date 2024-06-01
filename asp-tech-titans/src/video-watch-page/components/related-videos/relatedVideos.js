import React from "react";
import VideoCard from "./videoCard";
import "./relatedVideos.css";
import jsonData from "../../../db/videos.json";

function getVideoByTitle(jsonData, title) {
    return jsonData.find(obj => obj.videoTitle === title);
}

function relatedVideos({ RelatedVideos, setUrl }) {
    return (
        <div className="related-videos">
            <div className="video-list">
                {RelatedVideos.map((relateVideo) => (
                    <VideoCard
                    video={getVideoByTitle(jsonData, relateVideo.videoTitle)}
                    setUrl={setUrl} 
                    key={getVideoByTitle(jsonData, relateVideo.videoTitle).id} />
                ))}
            </div>
        </div>
    );
}

export default relatedVideos;