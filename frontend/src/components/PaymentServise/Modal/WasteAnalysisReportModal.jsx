/* eslint-disable no-unused-vars */
/* eslint-disable react/prop-types */
import React from 'react';
import { motion, AnimatePresence } from 'framer-motion';
import { X } from 'lucide-react';
import { LineChart, CartesianGrid, XAxis, YAxis, Tooltip, Legend, Line, ResponsiveContainer } from 'recharts';

const WasteAnalysisReportModal = ({ showWasteAnalysisReport, setShowWasteAnalysisReport }) => {
  const wasteData = [
    { year: 2006, general: 2500, recyclable: 100, organic: 0 },
    { year: 2008, general: 2000, recyclable: 500, organic: 50 },
    { year: 2010, general: 4000, recyclable: 1000, organic: 200 },
    { year: 2012, general: 10000, recyclable: 5000, organic: 1000 },
    { year: 2014, general: 4000, recyclable: 7000, organic: 2000 },
    { year: 2016, general: 3000, recyclable: 8000, organic: 3000 },
    { year: 2018, general: 2000, recyclable: 9000, organic: 4000 },
  ];

  return (
    <AnimatePresence>
      {showWasteAnalysisReport && (
        <motion.div
          className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50"
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          exit={{ opacity: 0 }}
        >
          <motion.div
            className="bg-white dark:bg-gray-800 rounded-3xl shadow-2xl w-[900px] h-[600px] p-8 overflow-y-auto"
            initial={{ scale: 0.9, y: 20 }}
            animate={{ scale: 1, y: 0 }}
            exit={{ scale: 0.9, y: 20 }}
            transition={{ type: "spring", stiffness: 300, damping: 30 }}
          >
            <div className="flex justify-between items-center mb-6">
              <h3 className="text-2xl font-bold text-emerald-600 dark:text-emerald-400">Waste Analysis Report</h3>
              <motion.button
                onClick={() => setShowWasteAnalysisReport(false)}
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
              <ResponsiveContainer width="100%" height={400}>
                <LineChart data={wasteData}>
                  <CartesianGrid strokeDasharray="3 3" />
                  <XAxis dataKey="year" />
                  <YAxis />
                  <Tooltip />
                  <Legend />
                  <Line type="monotone" dataKey="general" stroke="#FF6B6B" strokeWidth={2} name="General Waste" />
                  <Line type="monotone" dataKey="recyclable" stroke="#4ECDC4" strokeWidth={2} name="Recyclable Waste" />
                  <Line type="monotone" dataKey="organic" stroke="#45B7D1" strokeWidth={2} name="Organic Waste" />
                </LineChart>
              </ResponsiveContainer>
            </motion.div>

            {/* <motion.div
              initial={{ opacity: 0, y: 20 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ delay: 0.4 }}
              className="space-y-4"
            >
              <h4 className="text-xl font-semibold text-emerald-600 dark:text-emerald-400">Key Findings</h4>
              <ul className="list-disc list-inside space-y-2">
                <li>General waste peaked in 2012 but has been decreasing since then.</li>
                <li>Recyclable waste collection has been steadily increasing since 2006.</li>
                <li>Organic waste collection started in 2008 and has shown consistent growth.</li>
                <li>The overall trend shows a shift from general waste to more recyclable and organic waste collection.</li>
              </ul>
            </motion.div> */}
          </motion.div>
        </motion.div>
      )}
    </AnimatePresence>
  );
};

export default WasteAnalysisReportModal;