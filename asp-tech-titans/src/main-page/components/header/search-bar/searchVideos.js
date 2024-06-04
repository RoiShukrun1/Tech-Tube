// searchVideos.js

const searchVideos = (videos, query) => {
    if (query === '') {
      return videos;
    } else {
      const lowerCaseQuery = query.toLowerCase();
      return videos.filter(video =>
        video.videoTitle.toLowerCase().startsWith(lowerCaseQuery) ||
        video.publisher.toLowerCase() === lowerCaseQuery ||
        video.videoTitle.toLowerCase().split(' ').includes(lowerCaseQuery)
      );
    }
  };
  
  export default searchVideos;
  