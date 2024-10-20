/* eslint-disable no-unused-vars */
/* eslint-disable no-undef */
import { useState } from 'react';
import { motion, AnimatePresence ,setShowBadgeDetails} from 'framer-motion';
import {
    Award,  X,  Check
  } from 'lucide-react';
const BadgeDetails = (showBadgeDetails,) => (
    <AnimatePresence>
      {showBadgeDetails && (
        <motion.div
          className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50"
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          exit={{ opacity: 0 }}
        >
          <motion.div
            className="bg-white rounded-2xl shadow-2xl w-80 p-6"
            initial={{ scale: 0.9, y: 20 }}
            animate={{ scale: 1, y: 0 }}
            exit={{ scale: 0.9, y: 20 }}
            transition={{ type: "spring", stiffness: 300, damping: 30 }}
          >
            <div className="flex justify-between items-center border-b pb-4">
              <h3 className="text-xl font-bold text-blue-500 flex items-center">
                <Award className="h-6 w-6 mr-2 text-blue-500" />
                {badge.name}
              </h3>
              <motion.button
                onClick={() => setShowBadgeDetails(false)}
                className="text-gray-500 hover:text-gray-700 transition-colors duration-200"
                whileHover={{ rotate: 90 }}
                whileTap={{ scale: 0.9 }}
              >
                <X className="h-5 w-5" />
              </motion.button>
            </div>
            <div className="p-4">
              <div className="mb-4">
                <div className="flex justify-between items-center mb-1">
                  <span className="font-semibold text-sm">Progress</span>
                  <span className="text-blue-500 font-bold text-sm">{badge.progress}%</span>
                </div>
                <motion.div
                  className="bg-blue-500 h-2 rounded-full"
                  initial={{ width: 0 }}
                  animate={{ width: `${badge.progress}%` }}
                  transition={{ duration: 1, ease: "easeOut" }}
                />
              </div>
              <div className="mb-4">
                <h4 className="font-semibold mb-2 text-sm">Tasks:</h4>
                <ul className="space-y-1">
                  {badge.tasks.map((task, index) => (
                    <motion.li
                      key={index}
                      className="flex items-center text-sm"
                      initial={{ opacity: 0, x: -10 }}
                      animate={{ opacity: 1, x: 0 }}
                      transition={{ delay: index * 0.1 }}
                    >
                      <motion.span
                        className={`inline-flex items-center justify-center w-4 h-4 mr-2 border rounded-full ${task.completed ? 'border-green-500 bg-green-500' : 'border-gray-300'}`}
                        whileHover={{ scale: 1.2 }}
                        whileTap={{ scale: 0.9 }}
                      >
                        {task.completed && <Check className="h-3 w-3 text-white" />}
                      </motion.span>
                      <span className={task.completed ? 'text-gray-500' : ''}>{task.name}</span>
                    </motion.li>
                  ))}
                </ul>
              </div>
              <motion.p
                className="text-sm text-gray-600 bg-gray-100 p-3 rounded-lg"
                initial={{ opacity: 0, y: 10 }}
                animate={{ opacity: 1, y: 0 }}
                transition={{ delay: 0.4 }}
              >
                {badge.details}
              </motion.p>
            </div>
          </motion.div>
        </motion.div>
      )}
    </AnimatePresence>
  );
  

export default BadgeDetails