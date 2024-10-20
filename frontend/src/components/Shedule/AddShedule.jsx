import React, { useState, useEffect } from 'react';
import {
    TextField,
    Checkbox,
    FormControlLabel,
    Button,
    FormGroup,
    Select,
    MenuItem,
    InputLabel,
    Grid,
    Container,
    Card,
    Typography,
    Box,
    FormControl,
} from '@mui/material';
import axios from 'axios';

const AddSchedule = () => {
    const [scheduleId, setScheduleId] = useState('');
    const [selectedBins, setSelectedBins] = useState([]);
    const [driverId, setDriverId] = useState('');
    const [time, setTime] = useState('');
    const [route, setRoute] = useState('');
    const [drivers, setDrivers] = useState([]); // State to store fetched drivers

    const defaultBins = [
        { id: 'SB001', status: 'Full', Proccess: 'not' },
        { id: 'SB002', status: 'Full', Proccess: 'not' },
        { id: 'SB003', status: 'Full', Proccess: 'not' },
        { id: 'SB004', status: 'Full', Proccess: 'not' },
        { id: 'SB005', status: 'Full', Proccess: 'not' },
        { id: 'SB006', status: 'Available', Proccess: 'not' },
    ];

    // Fetch drivers from the API when the component is mounted
    useEffect(() => {
        const fetchDrivers = async () => {
            try {
                const response = await axios.get('http://localhost:8080/api/waste/drivers/all');
                const availableDrivers = response.data.filter(driver => driver.available === true); // Filter only available drivers
                setDrivers(availableDrivers); // Update state with available drivers
            } catch (error) {
                console.error('Error fetching drivers:', error);
            }
        };

        fetchDrivers(); // Call fetch function
    }, []); // Empty dependency array ensures it runs only once

    const handleSubmit = async (e) => {
        e.preventDefault();
        const newSchedule = {
            scheduleId,
            smartBins: selectedBins,
            driverId,
            time,
            route,
        };

        try {
            const response = await axios.post('http://localhost:8080/api/waste/schedule/add', newSchedule);
            console.log('Response:', response.data);
            alert('Schedule added successfully!');
            resetFormFields();
        } catch (error) {
            console.error('Error adding schedule:', error);
            alert('Failed to add schedule. Please try again.');
        }
    };

    const resetFormFields = () => {
        setScheduleId('');
        setSelectedBins([]);
        setDriverId('');
        setTime('');
        setRoute('');
    };

    const handleBinChange = (e) => {
        const { value, checked } = e.target;
        if (checked) {
            setSelectedBins([...selectedBins, value]);
        } else {
            setSelectedBins(selectedBins.filter((bin) => bin !== value));
        }
    };

    return (
        <div className="flex min-h-screen mt-20">
            <Container maxWidth="sm" sx={{ mt: 4 }}>
                <Card sx={{ p: 4 }}>
                    <Typography variant="h4" gutterBottom align="center">
                        Add New Schedule
                    </Typography>
                    <form onSubmit={handleSubmit}>
                        <Grid container spacing={3}>
                            <Grid item xs={12}>
                                <TextField
                                    fullWidth
                                    label="Schedule ID"
                                    variant="outlined"
                                    value={scheduleId}
                                    onChange={(e) => setScheduleId(e.target.value)}
                                    required
                                    placeholder="e.g., S00003"
                                />
                            </Grid>

                            <Grid item xs={12}>
                                <Typography variant="h6">Smart Bins</Typography>
                                <FormGroup row>
                                    {defaultBins
                                        .filter(bin => bin.status === 'Full' && bin.Proccess === 'not')
                                        .map((bin) => (
                                            <FormControlLabel
                                                key={bin.id}
                                                control={
                                                    <Checkbox
                                                        checked={selectedBins.includes(bin.id)}
                                                        onChange={handleBinChange}
                                                        value={bin.id}
                                                    />
                                                }
                                                label={bin.id}
                                            />
                                        ))}
                                </FormGroup>
                            </Grid>

                            {/* Driver ID selection */}
                            <Grid item xs={12}>
                                <FormControl fullWidth>
                                    <InputLabel id="driver-select-label">Driver ID</InputLabel>
                                    <Select
                                        labelId="driver-select-label"
                                        id="driver-select"
                                        value={driverId}
                                        onChange={(e) => setDriverId(e.target.value)}
                                        required
                                        label="Driver ID"
                                    >
                                        <MenuItem value="" disabled>
                                            Select Driver
                                        </MenuItem>
                                        {drivers.map((driver) => (
                                            <MenuItem key={driver.driverId} value={driver.driverId}>
                                                {driver.driverName} ({driver.driverId})
                                            </MenuItem>
                                        ))}
                                    </Select>
                                </FormControl>
                            </Grid>

                            <Grid item xs={12}>
                                <TextField
                                    fullWidth
                                    label="Time"
                                    type="time"
                                    InputLabelProps={{ shrink: true }}
                                    value={time}
                                    onChange={(e) => setTime(e.target.value)}
                                    required
                                />
                            </Grid>

                            <Grid item xs={12}>
                                <TextField
                                    fullWidth
                                    label="Route"
                                    variant="outlined"
                                    value={route}
                                    onChange={(e) => setRoute(e.target.value)}
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
                                        Add Schedule
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

export default AddSchedule;
