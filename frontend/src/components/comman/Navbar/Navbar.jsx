/* eslint-disable no-unused-vars */
// Navbar.js
import React, { useState, useEffect } from 'react';

const Navbar = () => {
    const [currentTime, setCurrentTime] = useState(new Date());

    useEffect(() => {
        const timer = setInterval(() => {
            setCurrentTime(new Date());
        }, 1000);

        return () => {
            clearInterval(timer);
        };
    }, []);

    const formatTime = (date) => {
        return date.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit', second: '2-digit' });
    };

    return (
        <header className="fixed top-0 left-0 right-0 z-50 backdrop-filter backdrop-blur-lg bg-opacity-80 bg-white shadow-lg">
            <div className="container mx-auto px-4 py-4 flex justify-between items-center">
                <h1 className="text-3xl font-bold" >Eco<span className="text-emerald-500">Sync</span></h1>
                <div className="text-lg font-semibold bg-emerald-100 text-emerald-800 rounded-full px-4 py-2 shadow-md">
                    {formatTime(currentTime)}
                </div>
            </div>

        </header>
    );
};

export default Navbar;
