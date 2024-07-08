import React from "react";
import VideoCard from "./videoCard";
import "./relatedVideos.css";
import { useContext, useEffect, useState } from "react";
import { VideoDataContext } from '../../../contexts/videoDataContext';
import { CurrentVideoContext } from '../../currentVideoContext';
import axios from 'axios';

// Function to get video by id
function getVideoById(jsonData, id) {
    console.log('jsonData:', jsonData);
    return jsonData.find(obj => obj.id === id);
}

function getObjectByUrl(jsonData, url) {
    return jsonData.find(obj => obj.videoUploaded === url);
}

// Related videos component
function RelatedVideos({ setMoreInfoPressed, setInputValue }) {

    const { videoData } = useContext(VideoDataContext);
    const { videoUrl } = useContext(CurrentVideoContext);
    const [relatedVideos, setRelatedVideos] = useState([]);

    const getRelatedVideos = async (id) => {
        return (await axios.get('http://localhost/api/users/ID/videos/' + id + '/relatedvideos')).data;
    }

    const currentVideo = getObjectByUrl(videoData, videoUrl);

    useEffect(() => {
        const fetchRelatedVideos = async () => {
            try {
                setRelatedVideos(await getRelatedVideos(currentVideo.id));
            } catch (error) {
                console.error('Error fetching video list:', error);
            }
        };

        fetchRelatedVideos();
    }, [videoUrl]);

    return (
        <div className="related-videos">
            <div className="video-list">
                {relatedVideos.map((relateVideo) => (
                    <VideoCard
                        video={relateVideo}
                        setMoreInfoPressed={setMoreInfoPressed}
                        setInputValue={setInputValue}
                        key={relateVideo.id}
                    />
                ))}
            </div>
        </div>
    );
}

export default RelatedVideos;
export { getVideoById };