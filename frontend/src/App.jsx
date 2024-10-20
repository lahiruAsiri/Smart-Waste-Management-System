import React, { useState } from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom'; // Use BrowserRouter, Routes, and Route
import Navbar from './components/comman/Navbar/Navbar';
import Sidebar from './components/comman/Sidebar/Sidebar';
import PaymentDashboardPage from './components/PaymentServise/DashboardPage/DashboardPage';
import Home from './Home';
import Schedule from './components/Shedule/Shedule';
import AddSchedule from './components/Shedule/AddShedule';
import ViewFullBins from './components/Shedule/ViewFullBins';
import AvailableDrivers from './components/Shedule/AvailableDrivers';
import UpdateSchedule from './components/Shedule/UpdateSchedule';
import DriverList from './components/Driver/DriverList';
import ScheduleDetails from './components/Driver/ScheduleDetails';
import WasteDetails from './components/Driver/WasteDetails';

import backgroundImage from './assets/backB.png';
import './App.css';

function App() {
  const [isDarkMode, setIsDarkMode] = useState(false);

  return (
    <BrowserRouter> {/* Wrap your entire app in BrowserRouter */}
      <div className={`flex flex-col h-screen ${isDarkMode ? 'dark' : ''}`}>
        <Navbar isDarkMode={isDarkMode} setIsDarkMode={setIsDarkMode} />
        <div className="flex flex-1 overflow-hidden">
          <Sidebar isDarkMode={isDarkMode} />
          <main
            style={{
              backgroundImage: `url(${backgroundImage})`,
              backgroundSize: 'cover',
              backgroundRepeat: 'no-repeat',
              backgroundPosition: 'center',
            }}
            className="flex-1 overflow-y-auto dark:bg-gray-900"
          >
            <Routes> {/* Define your routes here */}
              <Route path="/" element={<Home />} />
              <Route path="/schedule" element={<Schedule />} />
              <Route path="/addSchedule" element={<AddSchedule />} />
              <Route path="/viewFullBins" element={<ViewFullBins />} />
              <Route path="/availableDrivers" element={<AvailableDrivers />} />
              <Route path="/driverList" element={<DriverList />} />
              <Route path="/scheduleDetails" element={<ScheduleDetails />} />
              <Route path="/wasteDetails" element={<WasteDetails />} />
              <Route path="/updateSchedule/:scheduleId" element={<UpdateSchedule />} />
              <Route path="/dashboard" element={<PaymentDashboardPage isDarkMode={isDarkMode} />} />
            </Routes>
          </main>
        </div>
      </div>
    </BrowserRouter>
  );
}

export default App;