import React from 'react';
import techTitansLogo from '../../../../db/techTitansLogo.png';


function Logo() {
    return (
        <div className="logo-container">
            <img src={techTitansLogo} alt="Logo" style={{ height: '140px', filter: 'none', WebkitFilter: 'none' }}/>
        </div>
    );
}

export default Logo;
