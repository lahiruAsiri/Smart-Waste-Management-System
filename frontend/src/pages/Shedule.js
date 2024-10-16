// src/components/Schedule.js
import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom'; // Import useNavigate hook
import './Schedule.css'; // Import your CSS file for Schedule component

const Schedule = () => {
    const navigate = useNavigate(); // Initialize the navigate function

    // Default schedule data
    const [schedules, setSchedules] = useState([
        {
            scheduleId: 'S00001',
            smartBins: ['SB001', 'SB002', 'SB003', 'SB004'],
            driverId: 'D002',
            time: '10:30 am',
            route: 'Malabe to Kaduwela'
        },
        {
            scheduleId: 'S00002',
            smartBins: ['SB005', 'SB006', 'SB007', 'SB008'],
            driverId: 'D003',
            time: '11:30 am',
            route: 'Battaramulla to Malabe'
        }
    ]);

    // Future implementation: Fetch data from backend using Spring Boot
    /*
    useEffect(() => {
        // Example using fetch API
        fetch('http://your-backend-api/schedules')
            .then(response => response.json())
            .then(data => setSchedules(data))
            .catch(error => console.error('Error fetching schedules:', error));
    }, []);
    */

    // Handler to navigate to Add Schedule page
    const handleAddSchedule = () => {
        navigate('/add-schedule');
    };

    // Optional: Handlers for other buttons
    const handleViewFullBins = () => {
        // Navigate to View Full Bins page or perform desired action
        // navigate('/view-full-bins');
        alert('View Full Bins functionality is not implemented yet.');
    };

    const handleAvailabilityDrivers = () => {
        // Navigate to Availability Drivers page or perform desired action
        // navigate('/availability-drivers');
        alert('Availability Drivers functionality is not implemented yet.');
    };

    return (
        <div className="schedule-container">
            <div className="schedule-content">
                <h1 className='header'>All Schedules</h1>
                <div className="table-container">
                    <table className="schedule-table">
                        <thead>
                            <tr>
                                <th>Schedule Id</th>
                                <th>Smart Bins</th>
                                <th>Driver Id</th>
                                <th>Time</th>
                                <th>Route</th>
                            </tr>
                        </thead>
                        <tbody>
                            {schedules.map((schedule, index) => (
                                <tr key={index}>
                                    <td>{schedule.scheduleId}</td>
                                    <td>
                                        {schedule.smartBins.map(bin => (
                                            <span key={bin} className="bin-item">{bin}</span>
                                        ))}
                                    </td>
                                    <td>{schedule.driverId}</td>
                                    <td>{schedule.time}</td>
                                    <td>{schedule.route}</td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
                <div className="button-group">
                    <button className="action-button" onClick={handleAddSchedule}>Add Schedule</button>
                    <button className="action-button" onClick={handleViewFullBins}>View Full Bins</button>
                    <button className="action-button" onClick={handleAvailabilityDrivers}>Availability Drivers</button>
                </div>
            </div>
        </div>
    );
};

export default Schedule;
