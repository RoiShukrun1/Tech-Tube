import React from 'react';
import VideoPlayer from './components/video-player/VideoPlayer';
import VideoInfo from './components/video-info/videoInfo';
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
  console.log(videos);
  const [isFocused, setIsFocused] = useState(false);
  const [inputValue, setInputValue] = useState('');
  const [moreInfoPressed, setMoreInfoPressed] = useState(false);

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
                setMoreInfoPressed={setMoreInfoPressed}
                moreInfoPressed={moreInfoPressed}
              />
              <Comments
                comments={currentVideo.comments}
                currentUser={currentUser}
                setVideos={setVideos}
                currentVideoId={currentVideo.id}
                inputValue={inputValue}
                setInputValue={setInputValue}
                isFocused={isFocused}
                setIsFocused={setIsFocused} />
            </div>
          </div>

          <div className="col-5">
            <RelatedVideos
              relatedVideos={currentVideo.RelatedVideos}
              setUrl={setVideoUrl}
              setVideos={setVideos}
              setMoreInfoPressed={setMoreInfoPressed}
              videos={videos}
              setInputValue={setInputValue}
            />
          </div>
        </div>
      </div>

    </div>

  );
};

export default VideoWatchPage;

