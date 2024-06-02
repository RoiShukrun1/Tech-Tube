import './App.css';
// import VideoWatchPage from '../video-watch-page/video-watch-page';
import MainPage from '../main-page/mainPage';
import { useState } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';


function App() {

  const [videoUrl, setVideoUrl] = useState('https://www.youtube.com/watch?v=dQw4w9WgXcQ');

  const setUrl = (url) => {
    setVideoUrl(url);
  }

  return (
    <div>
      {/* <VideoWatchPage videoUrl={videoUrl} setUrl={setUrl} /> */}
      <MainPage setUrl={setUrl} />
    </div>
  );
}

export default App;
