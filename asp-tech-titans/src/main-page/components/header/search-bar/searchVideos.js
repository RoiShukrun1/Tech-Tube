
const searchVideos = (videos, query) => {
    if (query === '') {
      return videos;
    } else {
      const lowerCaseQuery = query.toLowerCase();
      return videos.filter(video =>
        video.title.toLowerCase().startsWith(lowerCaseQuery) ||
        video.publisher.toLowerCase() === lowerCaseQuery ||
        video.title.toLowerCase().split(' ').includes(lowerCaseQuery)
      );
    }
  };
  
  export default searchVideos;
  