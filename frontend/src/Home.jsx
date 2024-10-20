import React from 'react';
import { useNavigate } from 'react-router-dom'; // Import useNavigate for navigation
import backgroundImage from './assets/homeback.png'; // Adjust the path if necessary

const Home = () => {
  const navigate = useNavigate(); // Initialize useNavigate for programmatic navigation

  return (
    <div
      className="flex items-center justify-center h-screen bg-gray-100"
      style={{
        backgroundImage: `url(${backgroundImage})`,
        backgroundSize: 'cover',
        backgroundPosition: 'center',
      }}
    >
      <div className="flex flex-col items-start ml-8 mr-96"> {/* Added 'items-start' and 'ml-8' for left alignment */}
        <h1 className="text-5xl font-bold mb-4 text-black">Waste Management System</h1>
        <p className="text-base text-black-300 mb-8 text-left max-w-md">
        Our platform provides innovative solutions to manage waste efficiently, ensuring a cleaner and more sustainable environment. With features like smart scheduling and intelligent bin management, EcoSync helps communities and organizations optimize their waste handling processes. Explore our tools to streamline waste collection, monitor bin usage, and contribute to a greener future.
        </p>

        <div className="flex flex-col items-start"> {/* Changed alignment to 'items-start' */}
          <button
            className="bg-green-700 text-white px-8 py-3 rounded-lg mb-2 hover:bg-green-800 transition duration-300"
            onClick={() => navigate('/schedule')} // Navigate to the '/schedule' route
          >
            Schedule
          </button>
          <button
            className="bg-green-700 text-white px-7 py-3 rounded-lg hover:bg-green-800 transition duration-300"
            onClick={() => navigate('/driverList')} // Navigate to the '/bins' route
          >
            Driver List
          </button>
          <button
            className="bg-green-700 text-white mt-3 px-7 py-3 rounded-lg hover:bg-green-800 transition duration-300"
            onClick={() => navigate('/dashboard')} // Navigate to the '/bins' route
          >
            Payment Dashboard
          </button>
        </div>
      </div>
    </div>
  );
};

export default Home;
