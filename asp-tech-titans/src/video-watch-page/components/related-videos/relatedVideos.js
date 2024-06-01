import React from "react";
import VideoCard from "./videoCard";
import "./relatedVideos.css";
import jsonData from "../../../db/videos.json";

function getVideoByTitle(jsonData, title) {
    return jsonData.find(obj => obj.videoTitle === title);
}

function relatedVideos({ RelatedVideos }) {
    return (
        <div className="related-videos">
            <div className="video-list">
                {RelatedVideos.map((relateVideo) => (
                    <VideoCard video={getVideoByTitle(jsonData, relateVideo.videoTitle)} />
                ))}
            </div>
        </div>
    );
}

export default relatedVideos;