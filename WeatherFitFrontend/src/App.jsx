// ✅ App.jsx 전체 수정
import React, { useState, useEffect } from 'react';
import { BrowserRouter, Routes, Route, useLocation, Navigate } from 'react-router-dom';
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
import { HeaderSpacer } from "./layout/mainPage.style.js";
import Footer from "./layout/Footer.jsx";
import ForgotIdOrPassword from "./pages/ForgotIdOrPassword.jsx";
import LocationSearch from "./components/LocationSearch.jsx";
import WeatherSection from "./components/WeatherSection.jsx";
import Source from "./pages/Source.jsx";
import FindPw from "./pages/FindPw.jsx";

function Layout() {
    const [address, setAddress] = useState(() => localStorage.getItem("address") || "서울시 종로구");
    const [selectedDate, setSelectedDate] = useState(() => localStorage.getItem("selectedDate") || "today");
    const [weatherData, setWeatherData] = useState(null);
    const [recommendationData, setRecommendationData] = useState(null);
    const location = useLocation();
    const isLoggedIn = !!localStorage.getItem("token");

    const fetchWeatherData = async (inputAddress, isTomorrow) => {
        try {
            const res = await fetch(`/api/weather?address=${encodeURIComponent(inputAddress)}${isTomorrow ? "&tomorrow=true" : ""}`);
            if (!res.ok) throw new Error("날씨 요청 실패");
            const data = await res.json();
            setWeatherData(data);
        } catch (err) {
            console.error("❌ 날시 조회 실패:", err);
            setWeatherData(null);
        }
    };

    const fetchClothesRecommendation = async (inputAddress, isTomorrow) => {
        const token = localStorage.getItem("token");
        if (!token) {
            alert("로그인이 필요합니다.");
            return;
        }

        const bannerType = location.pathname.replace("/", "").toLowerCase();

        try {
            const res = await fetch(`/api/recommend`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${token}`,
                },
                body: JSON.stringify({
                    address: inputAddress,
                    tomorrow: isTomorrow,
                    bannerType: bannerType,
                }),
            });

            if (!res.ok) throw new Error("코디 추천 실패");
            const data = await res.json();
            console.log("✅ 추천 받은 데이터:", data);
            setRecommendationData(data);
        } catch (err) {
            console.error("❌ 코디 추천 실패:", err);
            setRecommendationData(null);
        }
    };

    const handleSearch = async (inputAddress, selectedDate) => {
        const isTomorrow = selectedDate === "tomorrow";
        setAddress(inputAddress);
        setSelectedDate(selectedDate);

        localStorage.setItem("address", inputAddress);
        localStorage.setItem("selectedDate", selectedDate);

        await fetchWeatherData(inputAddress, isTomorrow);

        const recommendPages = ["/Best", "/Suggestion", "/Outerwear", "/Consultation", "/Pants"];
        if (recommendPages.includes(location.pathname) && isLoggedIn) {
            await fetchClothesRecommendation(inputAddress, isTomorrow);
        }
    };

    useEffect(() => {
        handleSearch(address, selectedDate);
    }, []);

    useEffect(() => {
        const isTomorrow = selectedDate === "tomorrow";
        const recommendPages = ["/Best", "/Suggestion", "/Outerwear", "/Consultation", "/Pants"];
        if (recommendPages.includes(location.pathname) && isLoggedIn) {
            fetchClothesRecommendation(address, isTomorrow);
        }
    }, [location.pathname]);

    const hideHeaderFooter = (
        location.pathname === "/Login" ||
        location.pathname === "/JoinTheMembership" ||
        location.pathname === "/ForgotIdOrPassword"
    );

    return (
        <>
            {!hideHeaderFooter && <Header />}
            <HeaderSpacer />

            {!hideHeaderFooter &&
                !["/CustomerServiceCenter", "/MyPage", "/MyPageInfo", "/MyPagePassword"].includes(location.pathname) && (
                    <LocationSearch
                        onSearchComplete={handleSearch}
                        address={address}
                        selectedDate={selectedDate}
                    />
                )}

            {!hideHeaderFooter &&
                !["/CustomerServiceCenter", "/MyPageInfo", "/MyPagePassword"].includes(location.pathname) && (
                    <WeatherSection weatherData={weatherData} address={address} />
                )}

            <Routes>
                <Route path="/" element={<MainPage />} />
                <Route path="/Best" element={isLoggedIn ? <Best recommendationData={recommendationData} /> : <Navigate to="/Login" replace />} />
                <Route path="/Suggestion" element={isLoggedIn ? <Suggestion recommendationData={recommendationData} /> : <Navigate to="/Login" replace />} />
                <Route path="/Outerwear" element={isLoggedIn ? <Outerwear recommendationData={recommendationData} /> : <Navigate to="/Login" replace />} />
                <Route path="/Consultation" element={isLoggedIn ? <Consultation /> : <Navigate to="/Login" replace />} />
                <Route path="/Pants" element={isLoggedIn ? <Pants recommendationData={recommendationData} /> : <Navigate to="/Login" replace />} />
                <Route path="/Login" element={<Login />} />
                <Route path="/JoinTheMembership" element={<JoinTheMembership />} />
                <Route path="/MyPage" element={<MyPage />} />
                <Route path="/MyPageInfo" element={<MyPageInfo />} />
                <Route path="/MyPagePassword" element={<MyPagePassword />} />
                <Route path="/CustomerServiceCenter" element={<CustomerServiceCenter />} />
                <Route path="/ForgotIdOrPassword" element={<ForgotIdOrPassword />} />
                <Route path="/source" element={<Source />} />
                <Route path="/FindPw" element={<FindPw />} />
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