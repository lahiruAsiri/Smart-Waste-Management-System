// src/components/Footer.js
import React from 'react';
import './Footer.css'; // Import CSS for styling
import { FaFacebookF, FaTwitter, FaInstagram } from 'react-icons/fa'; // Social media icons

const Footer = () => {
    return (
        <footer className="footer">
            <div className="footer-content">
                <h2>Waste Management</h2>
                <div className="social-icons">
                    <FaFacebookF className="social-icon" />
                    <FaTwitter className="social-icon" />
                    <FaInstagram className="social-icon" />
                </div>
                <p>© 2024 Waste Management | All Rights Reserved</p>
            </div>
        </footer>
    );
};

export default Footer;
