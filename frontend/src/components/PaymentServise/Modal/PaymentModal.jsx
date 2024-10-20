/* eslint-disable no-unused-vars */
/* eslint-disable react/prop-types */
import React, { useState } from 'react';
import { motion, AnimatePresence } from 'framer-motion';
import { X, CreditCard, DollarSign, Info, Settings, Trash2, Recycle, Leaf } from 'lucide-react';

const PaymentModal = ({ showPaymentModal, setShowPaymentModal }) => {
  const [selectedPlan, setSelectedPlan] = useState('general');

  const plans = [
    { name: 'General Waste', icon: Trash2, amount: 2800, color: 'emerald' },
    { name: 'Recyclables', icon: Recycle, amount: 2000, color: 'blue' },
    { name: 'Organic', icon: Leaf, amount: 3000, color: 'green' },
  ];

  return (
    <AnimatePresence>
      {showPaymentModal && (
        <motion.div
          className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50"
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          exit={{ opacity: 0 }}
        >
          <motion.div
            className="bg-white rounded-3xl shadow-2xl w-[800px] p-8 flex"
            initial={{ scale: 0.9, y: 20 }}
            animate={{ scale: 1, y: 0 }}
            exit={{ scale: 0.9, y: 20 }}
            transition={{ type: "spring", stiffness: 300, damping: 30 }}
          >
            {/* Left side */}
            <div className="flex-1 pr-8 border-r">
              <div className="flex justify-between items-center border-b pb-4 mb-6">
                <h3 className="text-2xl font-bold text-emerald-700 flex items-center">
                  <CreditCard className="mr-2" /> Waste Management Payment
                </h3>
                <motion.button
                  onClick={() => setShowPaymentModal(false)}
                  className="text-gray-500 hover:text-gray-700 transition-colors duration-200"
                  whileHover={{ rotate: 90 }}
                  whileTap={{ scale: 0.9 }}
                >
                  <X className="h-6 w-6" />
                </motion.button>
              </div>
              <div className="space-y-6">
                <motion.div
                  className="flex justify-between items-center bg-emerald-100 p-4 rounded-xl"
                  initial={{ x: -20, opacity: 0 }}
                  animate={{ x: 0, opacity: 1 }}
                  transition={{ delay: 0.2 }}
                >
                  <span className="text-lg">Total Amount</span>
                  <span className="text-2xl font-bold text-emerald-700">$4000</span>
                </motion.div>
                <motion.div
                  className="space-y-3"
                  initial={{ y: 20, opacity: 0 }}
                  animate={{ y: 0, opacity: 1 }}
                  transition={{ delay: 0.4 }}
                >
                  <div className="space-y-2">
                    <div className="flex justify-between items-center">
                      <span className="flex items-center"><DollarSign className="h-5 w-5 mr-2 text-emerald-500" /> Monthly Payment</span>
                      <span>$2800</span>
                    </div>
                    <div className="flex justify-between items-center">
                      <span className="flex items-center"><Info className="h-5 w-5 mr-2 text-blue-500" /> Tax</span>
                      <span>$1000</span>
                    </div>
                    <div className="flex justify-between items-center">
                      <span className="flex items-center"><Settings className="h-5 w-5 mr-2 text-gray-500" /> Other Cost</span>
                      <span>$80</span>
                    </div>
                  </div>
                </motion.div>
              </div>
            </div>

            {/* Right side */}
            <div className="flex-1 pl-8">
              <h4 className="text-xl font-semibold mb-6 text-emerald-700">Payment Plan</h4>
              <div className="space-y-4">
                {plans.map((plan, index) => (
                  <motion.div
                    key={plan.name}
                    className={`p-4 rounded-xl cursor-pointer transition-all duration-300 ${selectedPlan === plan.name.toLowerCase()
                      ? `bg-${plan.color}-100 border-2 border-${plan.color}-500`
                      : 'bg-gray-100 hover:bg-gray-200'
                      }`}
                    onClick={() => setSelectedPlan(plan.name.toLowerCase())}
                    initial={{ x: 20, opacity: 0 }}
                    animate={{ x: 0, opacity: 1 }}
                    transition={{ delay: 0.2 + index * 0.1 }}
                    whileHover={{ scale: 1.03 }}
                    whileTap={{ scale: 0.98 }}
                  >
                    <div className="flex justify-between items-center">
                      <div className="flex items-center">
                        <plan.icon className={`h-6 w-6 mr-3 text-${plan.color}-500`} />
                        <span className="font-medium">{plan.name}</span>
                      </div>
                      <span className={`font-bold text-${plan.color}-600`}>${plan.amount}</span>
                    </div>
                  </motion.div>
                ))}
              </div>
              <motion.button
                className="w-full mt-6 bg-emerald-500 text-white py-3 rounded-full hover:bg-emerald-600 transition-colors duration-300 flex items-center justify-center font-semibold text-lg"
                whileHover={{ scale: 1.05 }}
                whileTap={{ scale: 0.95 }}
              >
                <DollarSign className="mr-2" />
                Payment Go
              </motion.button>
            </div>
          </motion.div>
        </motion.div>
      )}
    </AnimatePresence>
  );
};

export default PaymentModal;
