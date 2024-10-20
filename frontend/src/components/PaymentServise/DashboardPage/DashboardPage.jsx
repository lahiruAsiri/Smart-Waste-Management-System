/* eslint-disable no-undef */

/* eslint-disable no-unused-vars */
/* eslint-disable react/prop-types */
import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import {
  Trash2, User, Archive, CreditCard, Award, CheckCircle, Eye,
} from 'lucide-react';
import { motion, AnimatePresence } from 'framer-motion';
import PaymentModal from '../Modal/PaymentModal';
import PaymentHistoryModal from '../Modal/PaymentHistoryModal';
import BinRegistrationModal from '../Modal/BinRegistrationModal';
import CompletionMessage from '../Modal/CompletionMessage';
import BinDetailsModal from '../Modal/BinDetailsModal';
import WasteAnalysisReportModal from '../Modal/WasteAnalysisReportModal';



const StatCard = ({ title, value, icon: Icon, trend, onClick, hasBin, children, isDarkMode }) => (
  <motion.div
    className={`relative p-6 rounded-3xl overflow-hidden group transition-all duration-500
    ${isDarkMode ? 'bg-gradient-to-br from-emerald-900 to-teal-800 text-emerald-100' : 'bg-gradient-to-br from-emerald-100 to-teal-200 text-emerald-900'}
    hover:shadow-2xl transform hover:-translate-y-2 cursor-pointer`}
    onClick={onClick}
    whileHover={{ scale: 1.05 }}
    whileTap={{ scale: 0.98 }}
  >
    <Icon className={`h-8 w-8 ${isDarkMode ? 'text-emerald-300' : 'text-emerald-700'} mb-4`} />
    <h3 className="text-lg font-semibold mb-2">{title}</h3>
    <div className="flex items-end space-x-2">
      <p className="text-4xl font-bold">{value}</p>
      {trend && (
        <span className={`text-sm ${trend > 0 ? 'text-green-400' : 'text-red-400'}`}>
          {trend > 0 ? '↑' : '↓'} {Math.abs(trend)}%
        </span>
      )}
    </div>
    {hasBin && (
      <motion.div
        className="absolute top-2 right-2 bg-emerald-500 text-white p-1 rounded-full"
        initial={{ scale: 0 }}
        animate={{ scale: 1 }}
        transition={{ type: "spring", stiffness: 300, damping: 10 }}
      >
        <CheckCircle className="h-4 w-4" />
      </motion.div>
    )}
    {children}
  </motion.div>
);

const DashboardPage = ({ isDarkMode }) => {
  const { userId } = useParams();
  console.log(userId);
  const [showPaymentModal, setShowPaymentModal] = useState(false);
  const [showPaymentHistoryModal, setShowPaymentHistoryModal] = useState(false);
  const [showBinRegistrationModal, setShowBinRegistrationModal] = useState(false);
  const [showCompletionMessage, setShowCompletionMessage] = useState(false);
  const [hasBin, setHasBin] = useState(false);
  const [showBinDetails, setShowBinDetails] = useState(false);
  const [showWasteAnalysisReportModal, setShowWasteAnalysisReportModal] = useState(false);

  const [errors, setErrors] = useState({});
  const [isLoading, setIsLoading] = useState({
    nextPaymentDate: true,
    activeStatus: true,
    recyclingActivity: true,
    binStatus: true
  });

  useEffect(() => {
    const fetchData = async () => {
      setErrors({});
      setIsLoading({
        nextPaymentDate: true,
        activeStatus: true,
        recyclingActivity: true,
        binStatus: true
      });

      try {
        // Fetch next payment date
        const paymentResponse = await fetch(`http://localhost:8080/api/waste/Payment/nextPayment?userid=${userId}`, {
          method: 'GET',
          credentials: 'include',
          headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
          }
        });
        if (!paymentResponse.ok) {
          throw new Error(`Next Payment Date: HTTP error! status: ${paymentResponse.status}`);
        }
        const paymentData = await paymentResponse.text();
        if (paymentData && !isNaN(new Date(paymentData).getTime())) {
          setNextPaymentDate(new Date(paymentData).toLocaleDateString());
        } else {
          setNextPaymentDate('N/A');
        }
      } catch (error) {
        console.error('Error fetching next payment date:', error);
        setErrors(prevErrors => ({ ...prevErrors, nextPaymentDate: error.message }));
        setNextPaymentDate('Error');
      } finally {
        setIsLoading(prev => ({ ...prev, nextPaymentDate: false }));
      }

      try {
        // Fetch active status
        const statusResponse = await fetch(`http://localhost:8080/api/waste/users/status?userId=${userId}`, {
          method: 'GET',
          credentials: 'include',
          headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
          }
        });
        if (!statusResponse.ok) {
          throw new Error(`Active Status: HTTP error! status: ${statusResponse.status}`);
        }
        const statusData = await statusResponse.text();
        setActiveStatus(statusData || 'N/A');
      } catch (error) {
        console.error('Error fetching active status:', error);
        setErrors(prevErrors => ({ ...prevErrors, activeStatus: error.message }));
        setActiveStatus('Error');
      } finally {
        setIsLoading(prev => ({ ...prev, activeStatus: false }));
      }

      try {
        // Fetch recycling activity
        const activityResponse = await fetch(`http://localhost:8080/api/waste/collector/getcollecterdetails?userid=${userId}`, {
          method: 'GET',
          credentials: 'include',
          headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
          }
        });
        if (!activityResponse.ok) {
          throw new Error(`Recycling Activity: HTTP error! status: ${activityResponse.status}`);
        }
        const activityData = await activityResponse.json();
        setRecyclingActivity(activityData || []);
      } catch (error) {
        console.error('Error fetching recycling activity:', error);
        setErrors(prevErrors => ({ ...prevErrors, recyclingActivity: error.message }));
        setRecyclingActivity([]);
      } finally {
        setIsLoading(prev => ({ ...prev, recyclingActivity: false }));
      }

      try {
        // Check if user has a bin
        const binResponse = await fetch(`http://localhost:8080/api/waste/bins/check?userId=${userId}`, {
          method: 'GET',
          credentials: 'include',
          headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
          }
        });
        if (!binResponse.ok) {
          throw new Error(`Bin Status: HTTP error! status: ${binResponse.status}`);
        }
        const binData = await binResponse.json();
        setHasBin(binData.hasBin);
      } catch (error) {
        console.error('Error fetching bin status:', error);
        setErrors(prevErrors => ({ ...prevErrors, binStatus: error.message }));
        setHasBin(false);
      } finally {
        setIsLoading(prev => ({ ...prev, binStatus: false }));
      }
    };

    fetchData();
  }, [userId]);

  return (
    <div className="pt-28 pb-20 px-20 ml-14">
      <div className="max-w-7xl mx-auto">
        <div className="flex items-center justify-between mb-10">
          <h2 className={`text-4xl font-bold ${isDarkMode ? 'text-emerald-300' : 'text-emerald-700'}`}>
            Recycling Dashboard
          </h2>
          <button className="px-4 py-2 bg-blue-600 text-white rounded-full font-semibold flex items-center">
            <Award className="h-5 w-5 mr-2" />
            BLUE Badge
          </button>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-8 mb-12">
          <StatCard title="Next Payment Date" value="2024-05-25" icon={Trash2} trend={5.3} isDarkMode={isDarkMode} />
          <StatCard
            title="Recycling Bins"
            value={hasBin ? "1" : "0"}
            icon={Archive}
            trend={hasBin ? 100 : 0}
            onClick={() => setShowBinRegistrationModal(true)}
            hasBin={hasBin}
          >
            {/* Eye icon positioned at the bottom left */}
            <motion.div
              className="absolute bottom-2 right-2"
              initial={{ opacity: 0, scale: 0.9 }}
              animate={{ opacity: 1, scale: 1 }}
              transition={{ type: "spring", stiffness: 300, damping: 20 }}
            ><Eye
                className="h-6 w-6 text-emerald-700"
                onClick={(e) => {
                  e.stopPropagation();
                  setShowBinDetails(true);
                }}
              />
            </motion.div>
          </StatCard>
          <StatCard title="Active Status" value="Active" icon={User} trend={0.8} isDarkMode={isDarkMode} />
          <StatCard
            title="Monthly Payment"
            value="$4,000"
            icon={CreditCard}
            trend={7.2}
            onClick={() => setShowPaymentModal(true)}
            isDarkMode={isDarkMode}
          >
            <motion.div
              className="absolute bottom-2 right-2"
              initial={{ opacity: 0, scale: 0.9 }}
              animate={{ opacity: 1, scale: 1 }}
              transition={{ type: "spring", stiffness: 300, damping: 20 }}
            >
              <Eye
                className="h-6 w-6 text-emerald-700"
                onClick={(e) => {
                  e.stopPropagation();
                  setShowPaymentHistoryModal(true);
                }}
              />
            </motion.div>
          </StatCard>
        </div>

        <div className={`p-6 rounded-3xl ${isDarkMode ? 'bg-gray-800' : 'bg-white'} shadow-lg mb-12`}>
          <div className="flex justify-between items-center mb-6">
            <h2 className={`text-2xl font-semibold ${isDarkMode ? 'text-emerald-300' : 'text-emerald-700'}`}>
              Recent Recycling Activity
            </h2>
            <div className="flex space-x-4">
              <button className={`px-4 py-2 rounded-full text-sm ${isDarkMode ? 'bg-emerald-700 text-white' : 'bg-emerald-100 text-emerald-800'}`}>
                View All
              </button>
              <button
                className={`px-4 py-2 rounded-full text-sm ${isDarkMode ? 'bg-red-600 text-white' : 'bg-red-100 text-red-800'}`}
                onClick={() => setShowWasteAnalysisReportModal(true)}
              >
                Report
              </button>
            </div>
          </div>
          <ul className="space-y-4">
            {[
              { binId: "BIN101", driverName: "John Doe", collectionDate: "2024-10-12" },
              { binId: "BIN202", driverName: "Jane Smith", collectionDate: "2024-10-14" },
              { binId: "BIN303", driverName: "Alex Johnson", collectionDate: "2024-10-16" },
            ].map((schedule, i) => (
              <li key={i} className={`p-4 rounded-lg ${isDarkMode ? 'bg-gray-700' : 'bg-gray-50'}`}>
                <div className="flex items-center justify-between">
                  <span className="font-semibold">{schedule.binId}</span>
                  <span className="font-semibold">{schedule.driverName}</span>
                  <span className="text-sm text-gray-500">{schedule.collectionDate}</span>
                </div>
              </li>
            ))}
          </ul>

        </div>
      </div>
      {/* Payment Modal */}
      {showPaymentModal && <PaymentModal showPaymentModal={showPaymentModal} setShowPaymentModal={setShowPaymentModal} />}
      {showPaymentHistoryModal && <PaymentHistoryModal showPaymentHistory={showPaymentHistoryModal} setShowPaymentHistory={setShowPaymentHistoryModal} />}
      {showBinRegistrationModal && (
        <BinRegistrationModal
          setShowBinRegistrationModal={setShowBinRegistrationModal}
          setShowCompletionMessage={setShowCompletionMessage}
          setHasBin={setHasBin}
          showBinRegistrationModal={showBinRegistrationModal}
        />
      )}
      {showCompletionMessage && <CompletionMessage showCompletionMessage={showCompletionMessage} setShowCompletionMessage={setShowCompletionMessage} />}
      {showBinDetails && (
        <BinDetailsModal
          
          showBinDetails={showBinDetails}
          setShowBinDetails={setShowBinDetails}
        />
      )}


      {showWasteAnalysisReportModal && (
        <WasteAnalysisReportModal
          showWasteAnalysisReport={showWasteAnalysisReportModal}
          setShowWasteAnalysisReport={setShowWasteAnalysisReportModal}
        />
      )}

    </div>
  );
};

export default DashboardPage;
