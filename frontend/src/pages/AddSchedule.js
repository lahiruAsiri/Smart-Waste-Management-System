// src/components/AddSchedule.js
import React, { useState } from 'react';
import './AddSchedule.css'; // Import your CSS file for AddSchedule

const AddSchedule = () => {
    // Default data for Smart Bins and Drivers
    const defaultBins = [
        { id: 'SB001', status: 'Full' },
        { id: 'SB002', status: 'Available' },
        { id: 'SB003', status: 'Full' },
        { id: 'SB004', status: 'Available' },
        { id: 'SB005', status: 'Full' },
        { id: 'SB006', status: 'Available' },
    ];

    const availableDrivers = [
        { id: 'D001', name: 'John Doe', available: true },
        { id: 'D002', name: 'Jane Smith', available: true },
        { id: 'D003', name: 'Mike Johnson', available: false },
        { id: 'D004', name: 'Emily Davis', available: true },
    ];

    // State for form inputs
    const [scheduleId, setScheduleId] = useState('');
    const [selectedBins, setSelectedBins] = useState([]);
    const [driverId, setDriverId] = useState('');
    const [time, setTime] = useState('');
    const [route, setRoute] = useState('');

    // Handle form submission
    const handleSubmit = (e) => {
        e.preventDefault();
        // Here you would typically send the data to the backend
        // For now, we'll just log it
        const newSchedule = {
            scheduleId,
            smartBins: selectedBins,
            driverId,
            time,
            route,
        };
        console.log('New Schedule:', newSchedule);
        // Reset form
        setScheduleId('');
        setSelectedBins([]);
        setDriverId('');
        setTime('');
        setRoute('');
        alert('Schedule added successfully!');
    };

    // Handle Smart Bins selection
    const handleBinChange = (e) => {
        const { value, checked } = e.target;
        if (checked) {
            setSelectedBins([...selectedBins, value]);
        } else {
            setSelectedBins(selectedBins.filter((bin) => bin !== value));
        }
    };

    return (
        <div className="add-schedule-container">
            {/* Optional: Include the Slider */}
            
            <div className="form-container">
                <h2>Add New Schedule</h2>
                <form onSubmit={handleSubmit} className="schedule-form">
                    <div className="form-group">
                        <label htmlFor="scheduleId">Schedule ID:</label>
                        <input
                            type="text"
                            id="scheduleId"
                            value={scheduleId}
                            onChange={(e) => setScheduleId(e.target.value)}
                            required
                            placeholder="e.g., S00003"
                        />
                    </div>

                    <div className="form-group">
                        <label>Smart Bins:</label>
                        <div className="bins-list">
                            {defaultBins
                                .filter(bin => bin.status === 'Full') // Assuming you want to select only full bins
                                .map((bin) => (
                                    <div key={bin.id} className="bin-item">
                                        <input
                                            type="checkbox"
                                            id={bin.id}
                                            value={bin.id}
                                            checked={selectedBins.includes(bin.id)}
                                            onChange={handleBinChange}
                                        />
                                        <label htmlFor={bin.id}>{bin.id}</label>
                                    </div>
                                ))}
                        </div>
                    </div>

                    <div className="form-group">
                        <label htmlFor="driverId">Driver ID:</label>
                        <select
                            id="driverId"
                            value={driverId}
                            onChange={(e) => setDriverId(e.target.value)}
                            required
                        >
                            <option value="">Select Driver</option>
                            {availableDrivers
                                .filter(driver => driver.available)
                                .map((driver) => (
                                    <option key={driver.id} value={driver.id}>
                                        {driver.name} ({driver.id})
                                    </option>
                                ))}
                        </select>
                    </div>

                    <div className="form-group">
                        <label htmlFor="time">Time:</label>
                        <input
                            type="time"
                            id="time"
                            value={time}
                            onChange={(e) => setTime(e.target.value)}
                            required
                        />
                    </div>

                    <div className="form-group">
                        <label htmlFor="route">Route:</label>
                        <input
                            type="text"
                            id="route"
                            value={route}
                            onChange={(e) => setRoute(e.target.value)}
                            required
                            placeholder="e.g., Malabe to Kaduwela"
                        />
                    </div>

                    <button type="submit" className="submit-button">
                        Add Schedule
                    </button>
                </form>
            </div>
        </div>
    );
};

export default AddSchedule;
