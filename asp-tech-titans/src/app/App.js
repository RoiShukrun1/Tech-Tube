import './App.css';
import VideoWatchPage from '../video-watch-page/video-watch-page';
import { useState } from 'react';

function App() {

  const [videoUrl, setVideoUrl] = useState('/db/videos/1Digitalization; Where to start_.mp4');

  const setUrl = (url) => {
    setVideoUrl(url);
  }

  return (
    <div>
      <VideoWatchPage videoUrl={videoUrl} setUrl={setUrl} />
    </div>
  );
}

export default App;
