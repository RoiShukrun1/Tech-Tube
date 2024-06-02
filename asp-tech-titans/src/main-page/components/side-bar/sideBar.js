import React from 'react';
import './sideBar.css';
import Logo from './logo-icon/logo';
import homeIcon from './home-icon.svg';
import forYouIcon from './for-you-icon.svg';
import Subscriptions from './subscription-icon.svg';


function Sidebar() {
  return (
    <div className="sidebar" id="sidebar-wrapper">
      <div className="logo-container">
        <Logo />
      </div>
      <ul className="list-group list-group-flush">
        <li className="list-group-item"><img src={homeIcon} alt="Home" />
        Home</li>
        <li className="list-group-item"><img src={Subscriptions} alt="For You" />
        Subscriptions</li>
        <li className="list-group-item"> <img src={forYouIcon} alt="For You" />
        For you</li>
        <div className="divider"></div>
        <div className="categories-label">Categories</div> 
        <li className="list-group-item no-icon">Sitcoms</li>
        <li className="list-group-item no-icon">Music</li>
        <li className="list-group-item no-icon">Restaurants</li>
        <li className="list-group-item no-icon">Gaming</li>
        <li className="list-group-item no-icon">Mixes</li>
        <li className="list-group-item no-icon">Computer Programming</li>
        <li className="list-group-item no-icon">Satire</li>
        <li className="list-group-item no-icon">News</li>
        <li className="list-group-item no-icon">Tech House</li>
        <li className="list-group-item no-icon">Playlists</li>
        <li className="list-group-item no-icon">Computer History</li>
        <li className="list-group-item no-icon">More</li>
      </ul>
    </div>
  );
}

export default Sidebar;
