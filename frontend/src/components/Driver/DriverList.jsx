import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

// DriverList component for displaying all drivers
const DriverList = () => {
  const [drivers, setDrivers] = useState([]); // State to hold the list of drivers
  const [filter, setFilter] = useState(''); // State for the filter input
  const navigate = useNavigate(); // Hook to navigate to different routes

  // Fetch drivers from the API when the component mounts
  useEffect(() => {
    const fetchDrivers = async () => {
      try {
        const response = await axios.get('http://localhost:8080/api/waste/drivers/all');
        setDrivers(response.data); // Update state with fetched drivers
      } catch (error) {
        console.error('Error fetching drivers:', error);
      }
    };

    fetchDrivers();
  }, []); // Empty dependency array ensures this runs once on mount

  // Filter driver list by name or ID
  const filteredDrivers = drivers.filter(
    (driver) =>
      driver.driverName.toLowerCase().includes(filter.toLowerCase()) ||
      driver.driverId.toLowerCase().includes(filter.toLowerCase())
  );

  // Function to handle driver card click and navigate to schedule details page
  const handleDriverClick = (driverId) => {
    navigate(`/scheduleDetails`, { state: { driverId } });
  };

  return (
    <div className="relative min-h-screen flex justify-center ml-[20%]">
      <main className="pt-20">
        <div style={{ width: '60vw', height: 578, position: 'relative' }}>
          <div
            style={{
              left: 237,
              top: 15,
              position: 'absolute',
              color: '#ffffff',
              fontSize: 28.91,
              fontFamily: 'Poppins',
              fontWeight: '400',
              wordWrap: 'break-word',
            }}
          >
            All Drivers
          </div>

          <div
            style={{
              width: 666,
              height: 508,
              left: 0,
              top: 70,
              position: 'absolute',
              background: 'rgba(209.34, 226.31, 219.77, 0.45)',
              borderRadius: 18,
            }}
          />

          {/* Search input for filtering drivers */}
          <input
            type="text"
            placeholder="Enter driver name or ID"
            style={{
              width: '200px',
              padding: '10px',
              borderRadius: '8px',
              position: 'absolute',
              right: '10%',
              top: '15px',
            }}
            value={filter}
            onChange={(e) => setFilter(e.target.value)}
          />

          {/* Driver List Headers */}
          <div
            style={{
              position: 'absolute',
              left: '79px',
              top: '80px',
              width: '518px',
              display: 'flex',
              justifyContent: 'space-between',
              alignItems: 'center',
              padding: '0 10px',
            }}
          >
            <div style={{ color: '#000000', fontWeight: 'bold' }}>Driver ID</div>
            <div style={{ color: '#000000', fontWeight: 'bold' }}>Driver Name</div>
            <div style={{ color: '#000000', fontWeight: 'bold' }}>Availability</div>
          </div>

          {/* Render filtered driver list */}
          {filteredDrivers.map((driver, index) => (
            <div
              key={driver.driverId}
              onClick={() => handleDriverClick(driver.driverId)}
              style={{
                width: 518,
                height: 35,
                left: 79,
                top: 119 + index * 49,
                position: 'absolute',
                background: 'white',
                boxShadow: '4px 4px 4px rgba(0, 0, 0, 0.25) inset',
                borderRadius: 8,
                cursor: 'pointer',
              }}
            >
              {/* Driver ID */}
              <div
                style={{
                  position: 'absolute',
                  left: '30px',
                  top: '10px',
                  color: '#00603B',
                  fontSize: 16.13,
                  fontFamily: 'Poppins',
                  fontWeight: '400',
                }}
              >
                {driver.driverId}
              </div>
              {/* Driver Name */}
              <div
                style={{
                  position: 'absolute',
                  left: '200px',
                  top: '10px',
                  color: '#00603B',
                  fontSize: 16.13,
                  fontFamily: 'Poppins',
                  fontWeight: '400',
                }}
              >
                {driver.driverName}
              </div>
              {/* Driver Availability */}
              <div
                style={{
                  position: 'absolute',
                  right: '25px',
                  top: '10px',
                  color: driver.available ? '#28A745' : '#DC3545', // Green for available, red for not available
                  fontSize: 16.13,
                  fontFamily: 'Poppins',
                  fontWeight: '400',
                }}
              >
                {driver.available ? 'Available' : 'Not Available'}
              </div>
            </div>
          ))}
        </div>
      </main>
    </div>
  );
};

export default DriverList;
