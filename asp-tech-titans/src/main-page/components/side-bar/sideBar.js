import React, { useState } from 'react';
import './sideBar.css';
import Logo from './logo-icon/logo';
import homeIcon from '../../../db/icons/home-icon.svg';
import forYouIcon from '../../../db/icons/for-you-icon.svg';
import Subscriptions from '../../../db/icons/subscription-icon.svg';
import ScrollingMenuButton from './scrolling-menu-button/scrollingMenuButton';
import ScrollingMenu from './scrolling-menu/scrollingMenu';
import music from '../../../db/icons/headphone-icon.svg';
import trending from '../../../db/icons/fast-speed-icon.svg';
import gaming from '../../../db/icons/gamepad-icon.svg';
import news from '../../../db/icons/newspaper-icon.svg';
import podcast from '../../../db/icons/podcast-icon.svg';
import settings from '../../../db/icons/setting-line-icon.svg';
import report from '../../../db/icons/comment-blog-icon.svg';
import help from '../../../db/icons/question-mark-circle-outline-icon.svg';
import feedback from '../../../db/icons/pencil-icon.svg';

function Sidebar() {
  const [isMenuOpen, setIsMenuOpen] = useState(false);

  const toggleMenu = () => {
    setIsMenuOpen(!isMenuOpen);
  };

  return (
    <div>
      <ScrollingMenuButton isOpen={isMenuOpen} toggleMenu={toggleMenu} />
      <ScrollingMenu isOpen={isMenuOpen} toggleMenu={toggleMenu} />
      <div className="sidebar" id="sidebar-wrapper">
        <div className="logo-container">
          <Logo />
        </div>
        <div className="scrollable-menu">
          <ul className="list-group list-group-flush">
            <li className="list-group-item"><img src={homeIcon} alt="Home" /> Home</li>
            <li className="list-group-item"><img src={Subscriptions} alt="Subscriptions" /> Subscriptions</li>
            <li className="list-group-item"><img src={forYouIcon} alt="For You" /> For you</li>
            <div className="divider"></div>
            <div className="categories-label">Categories</div>
            <li className="list-group-item no-icon">Sitcoms</li>
            <li className="list-group-item no-icon">Music</li>
            <li className="list-group-item no-icon">Restaurants</li>
            <li className="list-group-item no-icon">Gaming</li>
            <li className="list-group-item no-icon">Mixes</li>
            <li className="list-group-item no-icon">Computers</li>
            <li className="list-group-item no-icon">Satire</li>
            <li className="list-group-item no-icon">News</li>
            <li className="list-group-item no-icon">Tech House</li>
            <li className="list-group-item no-icon">Playlists</li>
            <li className="list-group-item no-icon">History</li>
            <div className="divider"></div>
            <div className="categories-label">Explore</div>
            <li className="list-group-item"><img src={music} alt="Home" />Music</li>
            <li className="list-group-item"><img src={trending} alt="Home" />Trending</li>
            <li className="list-group-item"><img src={gaming} alt="Home" />Gaming</li>
            <li className="list-group-item"><img src={news} alt="Home" />News</li>
            <li className="list-group-item"><img src={podcast} alt="Home" />Podcast</li>
            <div className="divider"></div>
            <li className="list-group-item"><img src={settings} alt="Home" />Settings</li>
            <li className="list-group-item"><img src={report} alt="Home" />Report history</li>
            <li className="list-group-item"><img src={help} alt="Home" />Help</li>
            <li className="list-group-item"><img src={feedback} alt="Home" />Send feedback</li>
          </ul>
        </div>
      </div>
    </div>
  );
}

export default Sidebar;
