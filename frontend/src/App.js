// src/App.js
import React from 'react';
import { Routes, Route } from 'react-router-dom';
import Navbar from './components/nav'; // Ensure the path matches your file structure
import Schedule from './pages/Shedule'; // Import your Schedule component
import Footer from './components/Footer'; // Import your Schedule component
import AddSchedule from './pages/AddSchedule'; // Import AddSchedule component
import Slider from './components/SlideMenu'; // Import AddSchedule component



const App = () => {
  return (
    <div>
      <Navbar /> 
      <Slider />{/* Include the Navbar at the top */}
      <Routes>
        <Route path="/" element={<Schedule />} />
        <Route path="/add-schedule" element={<AddSchedule />} /> {/* New Route */}
        {/* You can add more routes here as needed */}
      </Routes>
      <Footer />
    </div>
  );
};

export default App;

