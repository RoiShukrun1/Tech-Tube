import './App.css';
import VideoWatchPage from '../video-watch-page/video-watch-page.js';

function App() {
  const videoUrl = 'https://www.youtube.com/watch?v=dQw4w9WgXcQ';

  return (
    <div>
      <h1>Hello World!</h1>
      <VideoWatchPage videoUrl={videoUrl} />
    </div>
  );
}

export default App;
