import React from 'react';
import Header from "./layout/Header";
import MainPage from "./pages/MainPage.jsx";
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Pants from "./pages/Pants.jsx";
import Consultation from "./pages/Consultation.jsx";
import Outerwear from "./pages/Outerwear.jsx";
import Suggestion from "./pages/Suggestion.jsx";
import Best from "./pages/Best.jsx";
import Login from "./pages/Login.jsx";
import JoinTheMembership from "./pages/JoinTheMembership.jsx";
import MyPage from "./pages/MyPage.jsx";
import CustomerServiceCenter from "./pages/CustomerServiceCenter.jsx";

function App() {
    return (
        <div>
            <BrowserRouter>
                <Header />

                <Routes>
                    <Route path="/" element={<MainPage />} />
                    <Route path="/Best" element={<Best />} />
                    <Route path="/Suggestion" element={<Suggestion />} />
                    <Route path="/Outerwear" element={<Outerwear />} />
                    <Route path="/Consultation" element={<Consultation />} />
                    <Route path="/Pants" element={<Pants />} />
                    <Route path="/Login" element={<Login />} />
                    <Route path="/JoinTheMembership" element={<JoinTheMembership />} />
                    <Route path="/MyPage" element={<MyPage />} />
                    <Route path="/CustomerServiceCenter" element={<CustomerServiceCenter />} />
                </Routes>

            </BrowserRouter>
        </div>
    );
}

export default App;
