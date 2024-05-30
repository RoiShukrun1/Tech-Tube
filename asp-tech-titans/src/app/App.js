import './App.css';
import VideoWatchPage from '../video-watch-page/video-watch-page';

function App() {
  const videoUrl = 'https://www.youtube.com/watch?v=dQw4w9WgXcQ';

  return (
    <div>
      <VideoWatchPage videoUrl={videoUrl} />
    </div>
  );
}

export default App;
