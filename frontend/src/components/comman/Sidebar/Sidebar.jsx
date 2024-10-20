// Sidebar.js
import React from 'react';
import { Trash2, User, Calendar, Truck, Archive, CreditCard, BarChart2, Settings } from 'lucide-react';
import { useNavigate } from 'react-router-dom'; // Import useNavigate

const Sidebar = () => {
    const navigate = useNavigate(); // Initialize navigate hook

    const menuItems = [
        { icon: BarChart2, label: 'Dashboard', path: '/' },
        { icon: User, label: 'Users', path: '/' },
        { icon: Calendar, label: 'Schedule', path: '/schedule' },
        { icon: Truck, label: 'Drivers', path: '/driverList' },
        { icon: Archive, label: 'Bins', path: '/' },
        { icon: CreditCard, label: 'Payments', path: '/dashboard' }, // Update the path if necessary
        { icon: Settings, label: 'Settings', path: '/' }, // Add the route later
    ];

    return (
        <aside className="fixed left-0 top-0 h-full w-24 flex flex-col items-center py-24 space-y-8 bg-gray-100 shadow-light-neumorphic transition-colors duration-500">
            <Trash2 className="h-12 w-12 text-emerald-600" />
            {menuItems.map((item, index) => (
                <div
                    key={index}
                    className="flex flex-col items-center justify-center w-16 h-16 rounded-2xl text-emerald-700 hover:bg-green-300 transition-all duration-500 cursor-pointer transform hover:scale-110"
                    onClick={() => navigate(item.path)} // Navigate to the selected path on click
                >
                    <item.icon className="h-6 w-6 mb-1" />
                    <span className="text-xs">{item.label}</span>
                </div>
            ))}
        </aside>
    );
};

export default Sidebar;