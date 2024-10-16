// src/Navbar.js
import React from 'react';
import './nav.css'; // Import CSS for styling

const Navbar = () => {
    return (
        <nav className="navbar">
            <div className="navbar-left">
                <img src="logo.png" alt="Logo" className="logo" />
                <span className="app-name">Waste Management</span>
            </div>
            <div className="navbar-center">
                <button className="nav-button">Home</button>
                <button className="nav-button">Smart Bin</button>
                <button className="nav-button">Drivers</button>
                <button className="nav-button">Schedule</button>
            </div>
            <div className="navbar-right">
                <button className="nav-button">Login</button>
            </div>
        </nav>
    );
};

export default Navbar;
