import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import {
  TextField,
  Button,
  Grid,
  Container,
  Card,
  Typography,
  Box,
} from '@mui/material';
import axios from 'axios';

const UpdateSchedule = () => {
  const { scheduleId } = useParams(); // Get scheduleId from URL parameters
  const navigate = useNavigate(); // Hook to programmatically navigate

  // State to hold schedule details
  const [schedule, setSchedule] = useState({
    scheduleId: '',
    smartBins: [],
    driverId: '',
    time: '',
    route: '',
  });

  // State for loading and error handling
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  // Fetch schedule details using the scheduleId when component mounts
  useEffect(() => {
    const fetchSchedule = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/api/waste/schedule/getSchedule?scheduleId=${scheduleId}`);
        setSchedule(response.data); // Set the fetched schedule data
        setLoading(false);
      } catch (err) {
        setError('Failed to fetch schedule details'); // Set error message
        setLoading(false);
      }
    };

    fetchSchedule();
  }, [scheduleId]); // Dependency array ensures this runs when scheduleId changes

  // Handle form submission for updating the schedule
  const handleUpdate = async (e) => {
    e.preventDefault(); // Prevent default form submission behavior
    try {
      await axios.put(`http://localhost:8080/api/waste/schedule/update/${scheduleId}`, schedule); // Send updated schedule to API
      navigate('/schedule'); // Redirect back to the schedule list after updating
    } catch (err) {
      setError('Failed to update schedule'); // Set error message on failure
    }
  };

  // Render loading and error states
  if (loading) {
    return <div>Loading schedule...</div>; // Loading state
  }

  if (error) {
    return <div>{error}</div>; // Error state
  }

  return (
    <div className="flex min-h-screen mt-20">
      <Container maxWidth="sm" sx={{ mt: 4 }}>
        <Card sx={{ p: 4 }}>
          <Typography variant="h4" gutterBottom align="center">
            Update Schedule
          </Typography>
          <form onSubmit={handleUpdate}>
            <Grid container spacing={3}>
              <Grid item xs={12}>
                <TextField
                  fullWidth
                  label="Smart Bins"
                  variant="outlined"
                  value={schedule.smartBins.join(', ')} // Display smart bins as comma-separated
                  onChange={(e) => setSchedule({ ...schedule, smartBins: e.target.value.split(', ') })} // Convert back to array on input change
                  required
                  placeholder="e.g., SB001, SB002"
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  fullWidth
                  label="Driver Id"
                  variant="outlined"
                  value={schedule.driverId}
                  onChange={(e) => setSchedule({ ...schedule, driverId: e.target.value })} // Update driverId state
                  required
                  placeholder="e.g., D001"
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  fullWidth
                  label="Time"
                  type="time"
                  InputLabelProps={{
                    shrink: true, // Shrink label for time input
                  }}
                  value={schedule.time}
                  onChange={(e) => setSchedule({ ...schedule, time: e.target.value })} // Update time state
                  required
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  fullWidth
                  label="Route"
                  variant="outlined"
                  value={schedule.route}
                  onChange={(e) => setSchedule({ ...schedule, route: e.target.value })} // Update route state
                  required
                  placeholder="e.g., City Center"
                />
              </Grid>
              <Grid item xs={12}>
                <Box display="flex" justifyContent="center">
                  <Button
                    variant="contained"
                    color="success"
                    type="submit"
                    size="large"
                  >
                    Update Schedule
                  </Button>
                </Box>
              </Grid>
            </Grid>
          </form>
        </Card>
      </Container>
    </div>
  );
};

export default UpdateSchedule;
