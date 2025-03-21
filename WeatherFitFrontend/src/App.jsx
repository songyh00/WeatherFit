import React from 'react';
import Header from "./layout/Header";
import MainPage from "./pages/MainPage.jsx";
import { BrowserRouter, Routes, Route } from 'react-router-dom';

function App() {
    return (
        <div>
            <BrowserRouter>
                <Header />

                <Routes>
                    <Route path="/" element={<MainPage />} />
                </Routes>

            </BrowserRouter>
        </div>
    );
}

export default App;
