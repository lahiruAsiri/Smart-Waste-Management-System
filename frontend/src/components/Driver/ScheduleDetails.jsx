import React, { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';
import axios from 'axios';
import { styled } from '@mui/material/styles';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell, { tableCellClasses } from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import Typography from '@mui/material/Typography';

// Styled components for the table cells
const StyledTableCell = styled(TableCell)(({ theme }) => ({
  [`&.${tableCellClasses.head}`]: {
    backgroundColor: theme.palette.common.black,
    color: theme.palette.common.white,
  },
  [`&.${tableCellClasses.body}`]: {
    fontSize: 14,
  },
}));

// Styled component for table rows
const StyledTableRow = styled(TableRow)(({ theme }) => ({
  '&:nth-of-type(odd)': {
    backgroundColor: theme.palette.action.hover,
  },
  // Hide last border
  '&:last-child td, &:last-child th': {
    border: 0,
  },
}));

// ScheduleDetails component to display filtered schedules based on driverId
const ScheduleDetails = () => {
  const [schedules, setSchedules] = useState([]); // State to hold all schedule details
  const [filteredSchedules, setFilteredSchedules] = useState([]); // State to hold filtered schedule details
  const location = useLocation(); // Hook to access passed state
  const { driverId } = location.state || {}; // Extract driverId from the passed state

  // Fetch all schedules from the API when the component mounts
  useEffect(() => {
    const fetchSchedules = async () => {
      try {
        const response = await axios.get('http://localhost:8080/api/waste/schedule/getSchedules');
        setSchedules(response.data); // Update state with all fetched schedules
      } catch (error) {
        console.error('Error fetching schedules:', error);
      }
    };

    fetchSchedules();
  }, []);

  // Filter schedules based on the driverId whenever schedules or driverId change
  useEffect(() => {
    if (driverId && schedules.length > 0) {
      const filtered = schedules.filter((schedule) => schedule.driverId === driverId);
      setFilteredSchedules(filtered); // Update state with filtered schedules
    }
  }, [driverId, schedules]);

  // If no driverId is provided, show a message
  if (!driverId) {
    return <Typography variant="h6" align="center">No driver selected.</Typography>;
  }

  return (
    <div className="min-h-screen flex justify-center">
      <main className="pt-20">
        {/* <Typography variant="h4" align="center" gutterBottom>
          Schedule for Driver ID: {driverId}
        </Typography> */}
        <h1 className='text-3xl mt-20 mb-6 text-[#EFFFCB] font-bold text-center'>Schedule for Driver ID</h1>
        <TableContainer component={Paper} style={{ width: '80%', margin: '0 auto' }}>
          <Table sx={{ minWidth: 700 }} aria-label="customized table">
            <TableHead>
              <TableRow>
                <StyledTableCell>Time</StyledTableCell>
                <StyledTableCell>Route</StyledTableCell>
                <StyledTableCell>Smart Bins</StyledTableCell> {/* New column for Smart Bin IDs */}
              </TableRow>
            </TableHead>
            <TableBody>
              {filteredSchedules.length > 0 ? (
                filteredSchedules.map((schedule, index) => (
                  <StyledTableRow key={index}>
                    <StyledTableCell>{schedule.time}</StyledTableCell>
                    <StyledTableCell>{schedule.route}</StyledTableCell>
                    <StyledTableCell>{schedule.smartBins.join(', ')}</StyledTableCell> {/* Display Smart Bin IDs */}
                  </StyledTableRow>
                ))
              ) : (
                <StyledTableRow>
                  <StyledTableCell colSpan={3} align="center">
                    No schedule available for this driver.
                  </StyledTableCell>
                </StyledTableRow>
              )}
            </TableBody>
          </Table>
        </TableContainer>
      </main>
    </div>
  );
};

export default ScheduleDetails;
