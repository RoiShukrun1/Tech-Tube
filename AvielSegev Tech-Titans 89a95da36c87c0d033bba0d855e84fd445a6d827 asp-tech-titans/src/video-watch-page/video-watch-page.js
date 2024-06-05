import React from 'react';
import VideoPlayer from './components/video-player/VideoPlayer';
import VideoInfo from './components/video-info/VideoInfo';
import Comments from './components/comments/comments';
import RelatedVideos from './components/related-videos/relatedVideos';
import jsonData from '../db/videos.json';
import usersData from "../db/users.json";
import { useState } from 'react';

function getUserObjById(usersData, id) {
  return usersData.find(obj => obj.id === id);
}

function checkIfUserIsSubscribed(currentUser, publisher) {
  return currentUser.subscriptions.includes(publisher);
}

function getObjectByUrl(jsonData, url) {
  return jsonData.find(obj => obj.videoUrl === url);
}

const VideoWatchPage = ({ videoUrl, setUrl }) => {

  const currentUser = getUserObjById(usersData, 1); // the current user for now is the first user in the users.json file

  const videoCorrispondingData = getObjectByUrl(jsonData, videoUrl);

  const [susbscribeButtonIsVisible, setSusbscribeButtonIsVisible] = 
  useState(!checkIfUserIsSubscribed(currentUser, videoCorrispondingData.publisher));

  return (
    <div className="container">
      <div className="row">
        <div className="col">

          <div className="video-watch-page">
            <VideoPlayer url={videoUrl} />
            <VideoInfo
              videoTitle={videoCorrispondingData.videoTitle}
              views={videoCorrispondingData.views}
              date={videoCorrispondingData.date}
              publisherImg={videoCorrispondingData.publisherImg}
              publisher={videoCorrispondingData.publisher}
              info={videoCorrispondingData.info}
              setSusbscribeButtonIsVisible={setSusbscribeButtonIsVisible}
              susbscribeButtonIsVisible={susbscribeButtonIsVisible} />
            <Comments comments={videoCorrispondingData.comments} />

          </div>
        </div>

        <div className="col">
          <RelatedVideos
            RelatedVideos={videoCorrispondingData.RelatedVideos}
            setUrl={setUrl}
            setSusbscribeButtonIsVisible={setSusbscribeButtonIsVisible}
            currentUser={currentUser} />
        </div>
      </div>
    </div>





  );
};

export default VideoWatchPage;
export { checkIfUserIsSubscribed };

