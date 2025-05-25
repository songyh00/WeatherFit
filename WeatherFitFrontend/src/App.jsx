import React, { useState, useEffect } from 'react';
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
import FindPw from "./pages/FindPw.jsx";

function Layout() {
    const [address, setAddress] = useState("");
    const [weatherData, setWeatherData] = useState(null);

    const fetchWeatherData = async (inputAddress) => {
        try {
            const res = await fetch(`/api/weather?address=${encodeURIComponent(inputAddress)}`);
            if (!res.ok) throw new Error("날씨 요청 실패");
            const data = await res.json();
            console.log("📦 받은 날씨 데이터:", data);
            setAddress(inputAddress);
            setWeatherData(data);
        } catch (err) {
            console.error("❌ 날씨 조회 실패:", err);
            setAddress(inputAddress);
            setWeatherData(null);
        }
    };

    // ✅ 앱 처음 진입 시 기본 지역 "서울시 종로구"로 날씨 요청
    useEffect(() => {
        fetchWeatherData("서울시 종로구");
    }, []);

    const location = useLocation();

    const hideHeaderFooter = (
        location.pathname === "/Login" ||
        location.pathname === "/JoinTheMembership" ||
        location.pathname === "/ForgotIdOrPassword"
    );

    return (
        <>
            {!hideHeaderFooter && <Header />}
            <HeaderSpacer />

            {/* 지역 검색창 */}
            {
                (!hideHeaderFooter &&
                    !["/CustomerServiceCenter", "/MyPage", "/MyPageInfo", "/MyPagePassword"].includes(location.pathname)) &&
                <LocationSearch onSearchComplete={fetchWeatherData} />
            }

            {/* 날씨 정보 표시 */}
            {
                (!hideHeaderFooter &&
                    !["/CustomerServiceCenter", "/MyPageInfo", "/MyPagePassword"].includes(location.pathname)) &&
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
