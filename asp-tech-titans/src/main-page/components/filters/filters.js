import React from 'react';
import './filters.css';

function Filters() {
  return (
    <div className="video-categories">
      <button type="button" className="filter-button active">All</button>
      <button type="button" className="filter-button">Computer programming</button>
      <button type="button" className="filter-button">Music</button>
      <button type="button" className="filter-button">Gaming</button>
      <button type="button" className="filter-button">Restaurants</button>
      <button type="button" className="filter-button">Mixes</button>
      <button type="button" className="filter-button">Barbells</button>
      <button type="button" className="filter-button">Sitcoms</button>
      <button type="button" className="filter-button">Satire</button>
      <button type="button" className="filter-button">Brawl Stars</button>
      <button type="button" className="filter-button">Functions</button>
      <button type="button" className="filter-button">Playlists</button>
      <button type="button" className="filter-button">UEFA Champions League</button>
      <button type="button" className="filter-button">CDJ</button>
      <button type="button" className="filter-button">News</button>
      <button type="button" className="filter-button">Live</button>
      <button type="button" className="filter-button">Sports</button>
    </div>
  );
}

export default Filters;
