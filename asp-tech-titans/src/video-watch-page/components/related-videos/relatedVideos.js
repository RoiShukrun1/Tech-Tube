import React from "react";
import VideoCard from "./VideoCard";
import "./relatedVideos.css";
import jsonData from "../../../db/videos.json";

function getVideoById(jsonData, id) {
    return jsonData.find(obj => obj.id === id);
}

function relatedVideos({ RelatedVideos, setUrl, setSusbscribeButtonIsVisible, currentUser }) {
    return (
        <div className="related-videos">
            <div className="video-list">
                {RelatedVideos.map((relateVideo) => (
                    <VideoCard
                    video={getVideoById(jsonData, relateVideo.id)}
                    setUrl={setUrl} 
                    key={getVideoById(jsonData, relateVideo.id).id}
                    setSusbscribeButtonIsVisible={setSusbscribeButtonIsVisible}
                    currentUser={currentUser} />
                ))}
            </div>
        </div>
    );
}

export default relatedVideos;