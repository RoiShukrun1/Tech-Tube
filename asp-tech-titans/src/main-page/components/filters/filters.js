import React, { useState, useRef, useEffect } from 'react';
import './filters.css';

/**
 * Filters component that displays a horizontal scrollable list of filter buttons.
 * Includes left and right scroll buttons to navigate through the list of filters.
 */
function Filters() {
  // State to keep track of the active filter
  const [activeFilter, setActiveFilter] = useState('All');
  // State to show/hide the left scroll button
  const [showLeftButton, setShowLeftButton] = useState(false);
  // State to show/hide the right scroll button
  const [showRightButton, setShowRightButton] = useState(true);
  // Reference to the filter container element
  const filterContainerRef = useRef(null);

  // List of filters to display
  const filters = [
    'All', 'Computer programming', 'Music', 'Gaming', 'Restaurants', 'Mixes', 'Barbells', 
    'Sitcoms', 'Satire', 'Brawl Stars', 'Functions', 'Playlists', 'UEFA Champions League', 
    'CDJ', 'News', 'Live', 'Sports'
  ];

  /**
   * Handles the click event for filter buttons.
   * Sets the active filter state.
   *
   * @param {string} filter - The filter that was clicked.
   */
  const handleFilterClick = (filter) => {
    setActiveFilter(filter);
  };

  /**
   * Handles the scroll right button click event.
   * Scrolls the filter container to the right.
   */
  const handleScrollRight = () => {
    if (filterContainerRef.current) {
      filterContainerRef.current.scrollBy({ left: filterContainerRef.current.clientWidth * 0.2, behavior: 'smooth' });
      setShowLeftButton(true);
    }
  };

  /**
   * Handles the scroll left button click event.
   * Scrolls the filter container to the left.
   */
  const handleScrollLeft = () => {
    if (filterContainerRef.current) {
      filterContainerRef.current.scrollBy({ left: -filterContainerRef.current.clientWidth * 0.2, behavior: 'smooth' });
      if (filterContainerRef.current.scrollLeft <= filterContainerRef.current.clientWidth * 0.2) {
        setShowLeftButton(false);
      }
    }
  };

  /**
   * Effect hook to check the scroll position of the filter container.
   * Shows/hides the scroll buttons based on the scroll position.
   */
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
