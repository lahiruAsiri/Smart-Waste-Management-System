/* eslint-disable no-unused-vars */
/* eslint-disable react/prop-types */
import React, { useState, useEffect } from 'react';
import { motion, AnimatePresence } from 'framer-motion';
import { X, Truck } from 'lucide-react';
import { BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer } from 'recharts';

const BinDetailsModal = ({ showBinDetails, setShowBinDetails, userId }) => {
  const [binDetails, setBinDetails] = useState(null);
  const [monthlyData, setMonthlyData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [binList, setBinList] = useState([]);
  const [currentBinIndex, setCurrentBinIndex] = useState(0);

  // Fetch bin details and monthly data
  useEffect(() => {
    if (showBinDetails && userId) {
      console.log("Modal is opening and API calls should be initiated");
      console.log("userId modal:", userId);
      setLoading(true);

      fetch(`http://localhost:8080/api/waste/Bin/getbindetails?userid=${userId}`, {
        method: 'GET',
        credentials: 'include',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
        },
      })
        .then(response => {
          if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
          }
          return response.json();
        })
        .then(data => {
          console.log("Received bin details response:", data);
          if (Array.isArray(data) && data.length > 0) {
            setBinList(data);
            setCurrentBinIndex(0);
            updateBinDetails(data[0]);
          } else {
            throw new Error('No bin data found');
          }
        })
        .catch(error => {
          console.error("Error during API calls:", error);
          setLoading(false);
        });
    }
  }, [showBinDetails, userId]);

  const updateBinDetails = (bin) => {
    // Set bin details except for the fillLevel initially
    setBinDetails({
      id: bin.binid,
      type: bin.binType,
      location: bin.location,
      capacity: bin.capacity,
      lastEmptied: '2023-10-10', // Adjust based on real data
      fillLevel: 0, // Placeholder until data is fetched
    });

    // Fetch monthly data for the selected bin
    fetch(`http://localhost:8080/api/waste/Bin/${bin.binid}/collections/monthly`)
      .then(response => {
        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }
        return response.json();
      })
      .then(data => {
        console.log("Parsed monthly data:", data);
        const formattedData = [{ month: 'September', waste: data.September }];
        setMonthlyData(formattedData);
      })
      .catch(error => {
        console.error("Error during monthly data API call:", error);
      });

    // Fetch fill level data
    fetch(`http://localhost:8080/api/waste/Bin/${bin.binid}/collections/monthly-total`)
      .then(response => {
        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }
        return response.json();
      })
      .then(data => {
        console.log("Received fill level data:", data);
        const fillLevel = data.Total; // Assuming 'Total' contains the fill level
        setBinDetails(prevDetails => ({
          ...prevDetails,
          fillLevel, // Update the fill level dynamically
        }));
        setLoading(false);
      })
      .catch(error => {
        console.error("Error during fill level API call:", error);
        setLoading(false);
      });
  };

  const handleSchedulePickup = () => {
    const binId = binDetails.id;
    const newStatus = "Scheduled";

    // Make the PUT request to update the bin's status
    fetch(`http://localhost:8080/api/waste/Bin/${binId}/status?newStatus=${newStatus}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
    })
      .then(response => {
        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }
        return response.text();
      })
      .then(data => {
        console.log(`Bin ${binId} status updated successfully: ${data}`);
        alert(`Pickup for bin ${binId} has been scheduled.`);
      })
      .catch(error => {
        console.error('Error scheduling bin pickup:', error);
        alert('Failed to schedule pickup. Please try again later.');
      });
  };

  const handleNextBin = () => {
    const nextIndex = (currentBinIndex + 1) % binList.length;
    setCurrentBinIndex(nextIndex);
    updateBinDetails(binList[nextIndex]);
  };

  const handlePrevBin = () => {
    const prevIndex = (currentBinIndex - 1 + binList.length) % binList.length;
    setCurrentBinIndex(prevIndex);
    updateBinDetails(binList[prevIndex]);
  };

  if (loading) {
    return (
      <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
        <div className="text-white">Loading...</div>
      </div>
    );
  }

  return (
    <AnimatePresence>
      {showBinDetails && binDetails && (
        <motion.div
          className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50"
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          exit={{ opacity: 0 }}
        >
          <motion.div
            className="bg-white dark:bg-gray-800 rounded-3xl shadow-2xl w-[700px] p-8"
            initial={{ scale: 0.9, y: 20 }}
            animate={{ scale: 1, y: 0 }}
            exit={{ scale: 0.9, y: 20 }}
            transition={{ type: 'spring', stiffness: 300, damping: 30 }}
          >
            <div className="flex justify-between items-center mb-6">
              <h3 className="text-2xl font-bold text-emerald-600 dark:text-emerald-400">Bin Details</h3>
              <motion.button
                onClick={() => setShowBinDetails(false)}
                className="text-gray-500 hover:text-gray-700 dark:text-gray-300 dark:hover:text-gray-100"
                whileHover={{ rotate: 90 }}
                whileTap={{ scale: 0.9 }}
              >
                <X className="h-6 w-6" />
              </motion.button>
            </div>

            <div className="grid grid-cols-2 gap-6">
              <motion.div
                className="bg-emerald-100 dark:bg-emerald-800 p-4 rounded-xl"
                initial={{ opacity: 0, x: -20 }}
                animate={{ opacity: 1, x: 0 }}
                transition={{ delay: 0.2 }}
              >
                <h4 className="text-lg font-semibold mb-2">Bin ID: {binDetails.id}</h4>
                <p><span className="font-medium">Type:</span> {binDetails.type}</p>
                <p><span className="font-medium">Location:</span> {binDetails.location}</p>
                <p><span className="font-medium">Capacity:</span> {binDetails.capacity}</p>
                <p><span className="font-medium">Last Emptied:</span> {binDetails.lastEmptied}</p>
              </motion.div>

              <motion.div
                className="bg-gray-100 dark:bg-gray-700 p-4 rounded-xl"
                initial={{ opacity: 0, x: 20 }}
                animate={{ opacity: 1, x: 0 }}
                transition={{ delay: 0.3 }}
              >
                <h4 className="text-lg font-semibold mb-2">Bin Collect Count</h4>
                <div className="relative pt-1">
                  <div className="flex mb-2 items-center justify-between">
                    <div>
                      <span className="text-xs font-semibold inline-block py-1 px-2 uppercase rounded-full text-emerald-600 bg-emerald-200">
                        {binDetails.fillLevel}%
                      </span>
                    </div>
                  </div>
                  <div className="overflow-hidden h-4 mb-4 text-xs flex rounded bg-emerald-200">
                    <motion.div
                      style={{ width: `${binDetails.fillLevel}%` }}
                      className="shadow-none flex flex-col text-center whitespace-nowrap text-white justify-center bg-emerald-500"
                      initial={{ width: 0 }}
                      animate={{ width: `${binDetails.fillLevel}%` }}
                      transition={{ duration: 1, ease: 'easeOut' }}
                    />
                  </div>
                </div>
              </motion.div>
            </div>

            <motion.div
              className="mt-6"
              initial={{ opacity: 0, y: 20 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ delay: 0.4 }}
            >
              <h4 className="text-lg font-semibold mb-4">Monthly Collection Data</h4>
              <ResponsiveContainer width="100%" height={200}>
                <BarChart data={monthlyData}>
                  <CartesianGrid strokeDasharray="3 3" />
                  <XAxis dataKey="month" />
                  <YAxis />
                  <Tooltip />
                  <Bar dataKey="waste" fill="#28B5B5" />
                </BarChart>
              </ResponsiveContainer>
            </motion.div>

            <div className="mt-6 flex justify-between">
              <button onClick={handlePrevBin} className="bg-gray-300 hover:bg-gray-400 text-gray-800 font-bold py-2 px-4 rounded">
                Previous
              </button>
              <button onClick={handleNextBin} className="bg-gray-300 hover:bg-gray-400 text-gray-800 font-bold py-2 px-4 rounded">
                Next
              </button>
              <button onClick={handleSchedulePickup} className="bg-emerald-600 hover:bg-emerald-700 text-white font-bold py-2 px-4 rounded">
                Schedule Pickup
              </button>
            </div>
          </motion.div>
        </motion.div>
      )}
    </AnimatePresence>
  );
};

export default BinDetailsModal;
