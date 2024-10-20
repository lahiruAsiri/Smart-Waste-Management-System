import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';

const WasteDetails = () => {
  const { scheduleId } = useParams();
  const [wasteDetails, setWasteDetails] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchWasteDetails = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/waste/getWasteDetails?scheduleId=${scheduleId}`);
        setWasteDetails(response.data);
      } catch (error) {
        setError('Error fetching waste details');
      } finally {
        setLoading(false);
      }
    };

    fetchWasteDetails();
  }, [scheduleId]);

  if (loading) return <div>Loading waste details...</div>;
  if (error) return <div>{error}</div>;

  return (
    <div className="waste-details">
      <h2>Waste Collection Details</h2>
      {wasteDetails.length > 0 ? (
        wasteDetails.map((detail, index) => (
          <div key={index}>
            <h4>Collector ID: {detail.collectorId}</h4>
            <p>Bin ID: {detail.binId}</p>
            <input
              type="date"
              value={new Date(detail.collectionDate).toISOString().split('T')[0]}
              onChange={(e) => {
                // Handle date change logic here
              }}
            />
          </div>
        ))
      ) : (
        <div>No waste details found.</div>
      )}
    </div>
  );
};

export default WasteDetails;
