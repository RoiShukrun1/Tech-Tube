import React, { useState, useRef, useEffect } from 'react';
import './filters.css';

function Filters() {
  const [activeFilter, setActiveFilter] = useState('All');
  const [showLeftButton, setShowLeftButton] = useState(false);
  const [showRightButton, setShowRightButton] = useState(true);
  const filterContainerRef = useRef(null);

  const filters = [
    'All', 'Computer programming', 'Music', 'Gaming', 'Restaurants', 'Mixes', 'Barbells', 
    'Sitcoms', 'Satire', 'Brawl Stars', 'Functions', 'Playlists', 'UEFA Champions League', 
    'CDJ', 'News', 'Live', 'Sports'
  ];

  const handleFilterClick = (filter) => {
    setActiveFilter(filter);
  };

  const handleScrollRight = () => {
    if (filterContainerRef.current) {
      filterContainerRef.current.scrollBy({ left: filterContainerRef.current.clientWidth * 0.2, behavior: 'smooth' });
      setShowLeftButton(true);
    }
  };

  const handleScrollLeft = () => {
    if (filterContainerRef.current) {
      filterContainerRef.current.scrollBy({ left: -filterContainerRef.current.clientWidth * 0.2, behavior: 'smooth' });
      if (filterContainerRef.current.scrollLeft <= filterContainerRef.current.clientWidth * 0.2) {
        setShowLeftButton(false);
      }
    }
  };

  useEffect(() => {
    const checkScrollPosition = () => {
      if (filterContainerRef.current) {
        const { scrollLeft, scrollWidth, clientWidth } = filterContainerRef.current;
        setShowLeftButton(scrollLeft > 0);
        setShowRightButton(scrollLeft < scrollWidth - clientWidth);
      }
    };

    const handleScroll = () => {
      checkScrollPosition();
    };

    const filterContainer = filterContainerRef.current;
    filterContainer.addEventListener('scroll', handleScroll);

    checkScrollPosition(); // Initial check

    return () => {
      filterContainer.removeEventListener('scroll', handleScroll);
    };
  }, []);

  return (
    <div className="filter-container">
      {showLeftButton && <button className="scroll-button left" onClick={handleScrollLeft}>‹</button>}
      <div className={`video-categories ${showLeftButton ? 'fade-left' : ''}`} ref={filterContainerRef}>
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
      {showRightButton && <button className="scroll-button right" onClick={handleScrollRight}>›</button>}
    </div>
  );
}

export default Filters;
