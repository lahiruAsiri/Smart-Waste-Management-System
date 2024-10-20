/* eslint-disable react/prop-types */
import { motion, AnimatePresence } from 'framer-motion';
import { CheckCircle } from 'lucide-react';

const CompletionMessage = ({ showCompletionMessage, setShowCompletionMessage }) => (
    <AnimatePresence>
        {showCompletionMessage && (
            <motion.div
                className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50"
                initial={{ opacity: 0 }}
                animate={{ opacity: 1 }}
                exit={{ opacity: 0 }}
            >
                <motion.div
                    className="bg-white dark:bg-gray-800 rounded-3xl shadow-2xl p-8 max-w-md"
                    initial={{ scale: 0.9, y: 20 }}
                    animate={{ scale: 1, y: 0 }}
                    exit={{ scale: 0.9, y: 20 }}
                    transition={{ type: "spring", stiffness: 300, damping: 30 }}
                >
                    <div className="text-center">
                        <motion.div
                            initial={{ scale: 0 }}
                            animate={{ scale: 1 }}
                            transition={{ type: "spring", stiffness: 300, damping: 10 }}
                        >
                            <CheckCircle className="h-16 w-16 text-emerald-500 mx-auto mb-4" />
                        </motion.div>
                        <h3 className="text-2xl font-bold text-emerald-600 dark:text-emerald-400 mb-2">Registration Successful!</h3>
                        <p className="text-gray-600 dark:text-gray-300 mb-6">Your new recycling bin has been registered successfully.</p>
                        <motion.button
                            onClick={() => setShowCompletionMessage(false)}
                            className="bg-emerald-500 text-white py-2 px-4 rounded-md hover:bg-emerald-600 transition duration-300"
                            whileHover={{ scale: 1.05 }}
                            whileTap={{ scale: 0.95 }}
                        >
                            Close
                        </motion.button>
                    </div>
                </motion.div>
            </motion.div>
        )}
    </AnimatePresence>
);

export default CompletionMessage;
