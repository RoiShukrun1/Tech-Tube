function videoInfo({ videoTitle, views, date} ) {
  return (
    <div>
      <h1>Video Title: {videoTitle}</h1>
      <h2>Views: {views}</h2>
      <p>Date: {date}</p>
    </div>
  );
}

export default videoInfo;