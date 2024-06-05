import './App.css';
import VideoWatchPage from '../video-watch-page/video-watch-page';
import MainPage from '../main-page/mainPage';

function App() {

  return (
    <div>
      <MainPage initVideoUrl={'/db/videos/1Digitalization; Where to start_.mp4'} />
    </div>
  );
}

export default App;
