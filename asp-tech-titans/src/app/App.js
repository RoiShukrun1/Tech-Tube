import './App.css';
import VideoWatchPage from '../video-watch-page/video-watch-page';
import { useState } from 'react';

function App() {

  const [videoUrl, setVideoUrl] = useState('https://www.youtube.com/watch?v=dQw4w9WgXcQ');

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
