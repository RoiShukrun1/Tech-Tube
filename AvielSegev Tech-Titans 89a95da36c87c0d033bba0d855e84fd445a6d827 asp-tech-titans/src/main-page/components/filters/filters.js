import React, { useState } from 'react';
import './filters.css';

function Filters() {
  const [activeFilter, setActiveFilter] = useState('All');

  const filters = [
    'All', 'Computer programming', 'Music', 'Gaming', 'Restaurants', 'Mixes', 'Barbells', 
    'Sitcoms', 'Satire', 'Brawl Stars', 'Functions', 'Playlists', 'UEFA Champions League', 
    'CDJ', 'News', 'Live', 'Sports'
  ];

  const handleFilterClick = (filter) => {
    setActiveFilter(filter);
  };

  return (
    <div className="video-categories">
      {filters.map(filter => (
        <button
          key={filter}
          type="button"
          className={`filter-button ${activeFilter === filter ? 'active' : ''}`}
          onClick={() => handleFilterClick(filter)}
        >
          {filter}
        </button>
      ))}
    </div>
  );
}

export default Filters;
