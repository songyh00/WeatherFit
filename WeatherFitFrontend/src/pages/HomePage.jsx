import React from 'react';
import Navbar from '../components/Navbar';
import '../App.css';

function HomePage() {
    return (
        <div className="center-container">
            <Navbar />
            <h2>Welcome to WeatherFit</h2>
        </div>
    );
}

export default HomePage;
