/**
 * Filters videos based on the search query.
 * 
 * @param {Array} videos - The array of video objects to search through.
 * @param {string} query - The search query.
 * @returns {Array} - The filtered array of videos matching the search query.
 */
const searchVideos = (videos, query) => {
  // If the query is an empty string, return all videos
  if (query === '') {
    return videos;
  } else {
    // Convert the query to lowercase for case-insensitive comparison
    const lowerCaseQuery = query.toLowerCase();
    // Filter videos based on the search query
    return videos.filter(video =>
      // Check if the video title starts with the query
      video.title.toLowerCase().startsWith(lowerCaseQuery) ||
      // Check if the publisher matches the query
      video.publisher.toLowerCase() === lowerCaseQuery ||
      // Check if any word in the video title matches the query
      video.title.toLowerCase().split(' ').includes(lowerCaseQuery)
    );
  }
};

export default searchVideos;
