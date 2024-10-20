/* eslint-disable react/prop-types */
/* eslint-disable no-unused-vars */

import React, { useEffect, useState } from 'react';
import { motion, AnimatePresence } from 'framer-motion';
import { X, FileText } from 'lucide-react';
import { LineChart, CartesianGrid, XAxis, YAxis, Tooltip, Line, ResponsiveContainer } from 'recharts';

const PaymentHistoryModal = ({ showPaymentHistory, setShowPaymentHistory, userId }) => {
    const [paymentHistory, setPaymentHistory] = useState([]);
    const [chartData, setChartData] = useState([]);

    useEffect(() => {
        if (showPaymentHistory) {
            // Fetch payment history data using the Fetch API
            fetch(`http://localhost:8080/api/waste/Payment/getdetails?userid=USER2`)
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    return response.json();
                })
                .then(data => {
                    const formattedData = data.map(payment => ({
                        id: payment.paymentId,
                        date: new Date(payment.paymentDate).toISOString().split('T')[0], // Format the date
                        amount: payment.paymentAmount,
                        status: 'Paid' // Assuming status is 'Paid'
                    }));
                    setPaymentHistory(formattedData);
                    setChartData(formattedData.map(payment => ({
                        date: payment.date,
                        amount: payment.amount
                    })).reverse());
                })
                .catch(error => {
                    console.error('Error fetching payment history:', error);
                });
        }
    }, [showPaymentHistory, userId]);

    return (
        <AnimatePresence>
            {showPaymentHistory && (
                <motion.div
                    className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50"
                    initial={{ opacity: 0 }}
                    animate={{ opacity: 1 }}
                    exit={{ opacity: 0 }}
                >
                    <motion.div
                        className="bg-white dark:bg-gray-800 rounded-3xl shadow-2xl w-[800px] p-8"
                        initial={{ scale: 0.9, y: 20 }}
                        animate={{ scale: 1, y: 0 }}
                        exit={{ scale: 0.9, y: 20 }}
                        transition={{ type: "spring", stiffness: 300, damping: 30 }}
                    >
                        <div className="flex justify-between items-center mb-6">
                            <h3 className="text-2xl font-bold text-emerald-600 dark:text-emerald-400">Payment History</h3>
                            <motion.button
                                onClick={() => setShowPaymentHistory(false)} // Close modal
                                className="text-gray-500 hover:text-gray-700 dark:text-gray-300 dark:hover:text-gray-100"
                                whileHover={{ rotate: 90 }}
                                whileTap={{ scale: 0.9 }}
                            >
                                <X className="h-6 w-6" />
                            </motion.button>
                        </div>

                        <motion.div
                            initial={{ opacity: 0, y: 20 }}
                            animate={{ opacity: 1, y: 0 }}
                            transition={{ delay: 0.2 }}
                            className="mb-8"
                        >
                            <ResponsiveContainer width="100%" height={200}>
                                <LineChart data={chartData}>
                                    <CartesianGrid strokeDasharray="3 3" />
                                    <XAxis dataKey="date" />
                                    <YAxis />
                                    <Tooltip />
                                    <Line type="monotone" dataKey="amount" stroke="#10B981" strokeWidth={2} dot={{ r: 4 }} />
                                </LineChart>
                            </ResponsiveContainer>
                        </motion.div>

                        <div className="space-y-4 max-h-60 overflow-y-auto">
                            {paymentHistory.map((payment, index) => (
                                <motion.div
                                    key={payment.id}
                                    className="bg-gray-100 dark:bg-gray-700 p-4 rounded-xl flex justify-between items-center"
                                    initial={{ opacity: 0, y: 20 }}
                                    animate={{ opacity: 1, y: 0 }}
                                    transition={{ delay: index * 0.1 }}
                                >
                                    <div>
                                        <p className="font-semibold">{payment.date}</p>
                                        <p className="text-sm text-gray-600 dark:text-gray-400">{payment.status}</p>
                                    </div>
                                    <div className="flex items-center">
                                        <p className="text-lg font-bold text-emerald-600 dark:text-emerald-400 mr-2">${payment.amount}</p>
                                        <motion.button
                                            className="bg-emerald-500 text-white p-2 rounded-full"
                                            whileHover={{ scale: 1.1 }}
                                            whileTap={{ scale: 0.9 }}
                                        >
                                            <FileText className="h-4 w-4" />
                                        </motion.button>
                                    </div>
                                </motion.div>
                            ))}
                        </div>
                    </motion.div>
                </motion.div>
            )}
        </AnimatePresence>
    );
};

export default PaymentHistoryModal;
