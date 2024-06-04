import React from 'react';
import VideoPlayer from './components/video-player/VideoPlayer';
import VideoInfo from './components/video-info/VideoInfo';
import Comments from './components/comments/comments';
import RelatedVideos from './components/related-videos/relatedVideos';
import jsonData from '../db/videos.json';
import usersData from "../db/users.json";
import { useState } from 'react';
import Header from '../main-page/components/header/header';

function getUserObjById(usersData, id) {
  return usersData.find(obj => obj.id === id);
}

function getObjectByUrl(jsonData, url) {
  return jsonData.find(obj => obj.videoUrl === url);
}

const VideoWatchPage = ({ initVideoUrl }) => {

  const [videoUrl, setVideoUrl] = useState(initVideoUrl);

  const [users, setUsers] = useState(usersData); // the current user for now is the first user in the users.json file

  const [videos, setVideos] = useState(jsonData);

  const currentVideo = getObjectByUrl(videos, videoUrl);
  const currentUser = getUserObjById(users, 1);

  return (
    <div>
      <Header />
      <div className="container">
        <div className="row">
          <div className="col">

            <div className="video-watch-page">
              <VideoPlayer url={videoUrl} />
              <VideoInfo
                currentVideo={currentVideo}
                currentUser={currentUser}
                setUsers={setUsers}
              />
              <Comments
                comments={currentVideo.comments}
                currentUser={currentUser}
                setVideos={setVideos}
                currentVideoId={currentVideo.id} />
            </div>
          </div>

          <div className="col">
            <RelatedVideos
              relatedVideos={currentVideo.RelatedVideos}
              setUrl={setVideoUrl}
              setVideos={setVideos}
            />
          </div>
        </div>
      </div>

    </div>

  );
};

export default VideoWatchPage;

