// src/components/SlideMenu.js
import React from 'react';
import './SlideMenu.css'; // Import CSS for styling

const SlideMenu = () => {
    const menuItems = [
        "Dashboard",
        "Smart User",
        "Schedule",
        "Drivers",
        "Smart Bin",
        "Payment"
    ];

    return (
        <div className="slide-menu">
            {menuItems.map((item, index) => (
                <button key={index} className="menu-button">
                    {item}
                </button>
            ))}
        </div>
    );
};

export default SlideMenu;
