import React, { useState, useEffect } from 'react';
import { styled } from '@mui/material/styles';
import { useNavigate } from 'react-router-dom';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell, { tableCellClasses } from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import Button from '@mui/material/Button';

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

const ViewFullBins = () => {
  const navigate = useNavigate(); // Hook for programmatic navigation

  // State to hold the list of full bins
  const [fullBins, setFullBins] = useState([
    {
      binId: 'SB001',
      location: 'Malabe',
      status: 'Full',
      lastCollected: '2024-10-10 10:30 am',
    },
    {
      binId: 'SB002',
      location: 'Kaduwela',
      status: 'Full',
      lastCollected: '2024-10-09 09:45 am',
    },
    {
      binId: 'SB003',
      location: 'Nugegoda',
      status: 'Full',
      lastCollected: '2024-10-08 11:15 am',
    },
    {
      binId: 'SB004',
      location: 'Colombo 7',
      status: 'Full',
      lastCollected: '2024-10-11 08:00 am',
    },
    {
      binId: 'SB005',
      location: 'Dehiwala',
      status: 'Full',
      lastCollected: '2024-10-09 07:30 am',
    },
    {
      binId: 'SB006',
      location: 'Moratuwa',
      status: 'Full',
      lastCollected: '2024-10-10 09:00 am',
    },
    {
      binId: 'SB007',
      location: 'Pettah',
      status: 'Full',
      lastCollected: '2024-10-11 12:45 pm',
    },
    {
      binId: 'SB008',
      location: 'Kollupitiya',
      status: 'Full',
      lastCollected: '2024-10-09 03:30 pm',
    },
    {
      binId: 'SB009',
      location: 'Battaramulla',
      status: 'Full',
      lastCollected: '2024-10-08 06:15 am',
    },
    {
      binId: 'SB010',
      location: 'Kelaniya',
      status: 'Full',
      lastCollected: '2024-10-10 02:00 pm',
    },
    
    // Placeholder for additional bins
    // Duplicate entries can be replaced or adjusted based on actual data
  ]);

  // Placeholder for future data fetching from the backend using Spring Boot
  useEffect(() => {
    // Future implementation: Fetch data from the backend
    /*
    fetch('http://your-backend-api/fullBins')
      .then(response => response.json())
      .then(data => setFullBins(data))
      .catch(error => console.error('Error fetching full bins data:', error));
    */
  }, []); // Empty dependency array means this runs once after the component mounts

  return (
    <div className="flex min-h-screen">
      <div className="ml-20 mt-24 p-10 flex-1 bg-cover bg-center bg-no-repeat bg-fixed" style={{ backgroundImage: "url('../assets/backB.png')" }}>
        <h1 className='text-3xl mb-6 text-[#EFFFCB] font-bold text-center'>Full Bins</h1>

        {/* Table Container for displaying full bins */}
        <TableContainer component={Paper}>
          <Table sx={{ minWidth: 700 }} aria-label="customized table">
            <TableHead>
              <TableRow>
                <StyledTableCell>Bin ID</StyledTableCell>
                <StyledTableCell>Location</StyledTableCell>
                <StyledTableCell>Status</StyledTableCell>
                <StyledTableCell>Last Collected</StyledTableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {fullBins.map((bin) => (
                <StyledTableRow key={bin.binId}>
                  <StyledTableCell>{bin.binId}</StyledTableCell>
                  <StyledTableCell>{bin.location}</StyledTableCell>
                  <StyledTableCell>{bin.status}</StyledTableCell>
                  <StyledTableCell>{bin.lastCollected}</StyledTableCell>
                </StyledTableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>

        {/* Button for adding a schedule for bin collection */}
        <div className="mt-10 flex justify-center">
          <Button variant="contained" color="success" onClick={() => navigate('/addSchedule')}>
            Add Schedule for Collecting this Bins
          </Button>
        </div>
      </div>
    </div>
  );
};

export default ViewFullBins;
