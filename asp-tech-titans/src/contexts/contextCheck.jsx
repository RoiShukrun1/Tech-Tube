import React, { useContext } from 'react';
import { VideoContext } from './videoContext'; // Import the VideoContext
import { AccountContext } from './accountContext'; // Import the AccountContext
import { LoginContext } from './loginContext'; // Import the LoginContext
import { VideoDataContext } from './videoDataContext';

const VideoList = () => {
  const { videos } = useContext(VideoContext); 
  const { accounts } = useContext(AccountContext);
  const { login } = useContext(LoginContext);
  const { videoData } = useContext(VideoDataContext);

  return (
    <div>
      <h2>Uploaded Videos</h2>
      <ul>
        {videos.map((video, index) => (
          <li key={index}>
            <video src={video.url} controls width="300" />
          </li>
        ))}
      </ul>
      <div>
      <h2>Accounts:</h2>
      <ul>
        {accounts.map((account, index) => (
          <li key={index}>
            <p>Username: {account.username}</p>
            <p>Password: {account.password}</p>
            <p>Nickname: {account.nickname}</p>
            <img src={account.image} alt="User uploaded" style={{ width: '150px', height: '100px' }} />
          </li>
        ))}
      </ul>
    </div>
    <div>
      <h2>Login account:</h2>
      {login ? (
          <div>
            <p>Username: {login.username}</p>
            <p>Nickname: {login.nickname}</p>
            <p>Password: {login.password}</p>
            <img src={login.image} alt="User uploaded" style={{ width: '150px', height: '100px' }} />
          </div>
        ) : (
          <p>No user is logged in.</p>
        )}
    </div>
    <div>
      <h2>Video Data:</h2>
      <ul>
            {videoData.map((newVideoData, index) => (
            <li key={index}>
              <p>Title: {newVideoData.title}</p>
              <p>Description: {newVideoData.description}</p>
              <p>Tags: {newVideoData.tags}</p>
              <p>Category: {newVideoData.playlist}</p>
              <img src={newVideoData.thumbnail} alt="User uploaded" style={{ width: '150px', height: '100px' }} />
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
};

export default VideoList;