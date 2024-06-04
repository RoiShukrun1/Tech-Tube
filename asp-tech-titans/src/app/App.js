import './App.css';
import VideoWatchPage from '../video-watch-page/video-watch-page';
import { useState } from 'react';

function App() {

  return (
    <div>
      <VideoWatchPage initVideoUrl={'/db/videos/1Digitalization; Where to start_.mp4'} />
    </div>
  );
}

export default App;
