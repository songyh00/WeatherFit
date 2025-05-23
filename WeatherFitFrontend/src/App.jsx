import React, { useState } from 'react';
import { BrowserRouter, Routes, Route, useLocation } from 'react-router-dom';
import Header from "./layout/Header";
import MainPage from "./pages/MainPage.jsx";
import Pants from "./pages/Pants.jsx";
import Consultation from "./pages/Consultation.jsx";
import Outerwear from "./pages/Outerwear.jsx";
import Suggestion from "./pages/Suggestion.jsx";
import Best from "./pages/Best.jsx";
import Login from "./pages/Login.jsx";
import JoinTheMembership from "./pages/JoinTheMembership.jsx";
import MyPage from "./pages/MyPage.jsx";
import MyPageInfo from "./pages/MyPageInfo.jsx";
import MyPagePassword from "./pages/MyPagePassword.jsx";
import CustomerServiceCenter from "./pages/CustomerServiceCenter.jsx";
import { HeaderBackground } from "./layout/header.style.js";
import Footer from "./layout/Footer.jsx";
import ForgotIdOrPassword from "./pages/ForgotIdOrPassword.jsx";
import LocationSearch from "./components/LocationSearch.jsx";
import { HeaderSpacer } from "./layout/mainPage.style.js";
import WeatherSection from "./components/WeatherSection.jsx";

function Layout() {
    const [address, setAddress] = useState("");
    const [weatherData, setWeatherData] = useState(null);

    const fetchWeatherData = async (inputAddress) => {
        try {
            const res = await fetch(`/api/weather?address=${encodeURIComponent(inputAddress)}`);
            const data = await res.json();
            console.log("📦 받은 날씨 데이터:", data); // 이 부분 추가!
            setAddress(inputAddress);
            setWeatherData(data);
        } catch (err) {
            console.error("날씨 조회 실패:", err);
        }
    };

    const location = useLocation();
    const hideHeaderFooter = location.pathname === "/Login" || location.pathname === "/JoinTheMembership" || location.pathname === "/ForgotIdOrPassword";

    return (
        <>
            {!hideHeaderFooter && <Header />}

            <HeaderSpacer />

            {/* 지역검색창 */}
            {
                (!hideHeaderFooter && !["/CustomerServiceCenter", "/MyPage", "/MyPageInfo", "/MyPagePassword"].includes(location.pathname)) &&
                <LocationSearch onSearchComplete={fetchWeatherData} />
            }

            {/* 날씨 띄우는 공간 */}
            {
                (!hideHeaderFooter && !["/CustomerServiceCenter", "/MyPageInfo", "/MyPagePassword"].includes(location.pathname)) &&
                <WeatherSection weatherData={weatherData} address={address} />
            }

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
                <Route path="/MyPageInfo" element={<MyPageInfo />} />
                <Route path="/MyPagePassword" element={<MyPagePassword />} />
                <Route path="/CustomerServiceCenter" element={<CustomerServiceCenter />} />
                <Route path="/ForgotIdOrPassword" element={<ForgotIdOrPassword />} />
            </Routes>

            {!hideHeaderFooter && <Footer />}
        </>
    );
}

export default function App() {
    return (
        <BrowserRouter>
            <Layout />
        </BrowserRouter>
    );
}
