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

// ✅ API Hook import
import { useWeatherApi } from './api/weather';
import { useRecommendApi } from './api/recommend';

function Layout() {
    const [address, setAddress] = useState(() => localStorage.getItem("address") || "서울시 종로구");
    const [selectedDate, setSelectedDate] = useState(() => localStorage.getItem("selectedDate") || "today");
    const [weatherData, setWeatherData] = useState(null);
    const [recommendationData, setRecommendationData] = useState(null);
    const [isLoading, setIsLoading] = useState(false);
    const location = useLocation();
    const isLoggedIn = !!localStorage.getItem("token");

    const { fetchWeather } = useWeatherApi();
    const { fetchRecommendation } = useRecommendApi();

    const handleSearch = async (inputAddress, selectedDate) => {
        const isTomorrow = selectedDate === "tomorrow";
        setAddress(inputAddress);
        setSelectedDate(selectedDate);

        localStorage.setItem("address", inputAddress);
        localStorage.setItem("selectedDate", selectedDate);

        const weather = await fetchWeather(inputAddress, isTomorrow);
        setWeatherData(weather);

        const recommendPages = ["/Best", "/Suggestion", "/Outerwear", "/Consultation", "/Pants"];
        if (recommendPages.includes(location.pathname) && isLoggedIn) {
            const pathToBannerType = {
                "/Best": "BEST",
                "/Suggestion": "RECOMMEND",
                "/Outerwear": "OUTER",
                "/Consultation": "TOP",
                "/Pants": "BOTTOM"
            };
            const bannerType = pathToBannerType[location.pathname];

            if (bannerType) {
                setIsLoading(true);
                const recommendation = await fetchRecommendation(inputAddress, isTomorrow, bannerType);
                setRecommendationData(recommendation);
                setIsLoading(false);
            }
        }
    };

    useEffect(() => {
        handleSearch(address, selectedDate);
    }, []);

    useEffect(() => {
        const isTomorrow = selectedDate === "tomorrow";
        const recommendPages = ["/Best", "/Suggestion", "/Outerwear", "/Consultation", "/Pants"];
        if (recommendPages.includes(location.pathname) && isLoggedIn) {
            const pathToBannerType = {
                "/Best": "BEST",
                "/Suggestion": "RECOMMEND",
                "/Outerwear": "OUTER",
                "/Consultation": "TOP",
                "/Pants": "BOTTOM"
            };
            const bannerType = pathToBannerType[location.pathname];

            if (bannerType) {
                setIsLoading(true);
                fetchRecommendation(address, isTomorrow, bannerType).then(data => {
                    setRecommendationData(data);
                    setIsLoading(false);
                });
            }
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
                <Route path="/Best" element={isLoggedIn ? <Best recommendationData={recommendationData} isLoading={isLoading} /> : <Navigate to="/Login" replace />} />
                <Route path="/Suggestion" element={isLoggedIn ? <Suggestion recommendationData={recommendationData} isLoading={isLoading} /> : <Navigate to="/Login" replace />} />
                <Route path="/Outerwear" element={isLoggedIn ? <Outerwear recommendationData={recommendationData} isLoading={isLoading} /> : <Navigate to="/Login" replace />} />
                <Route path="/Consultation" element={isLoggedIn ? <Consultation recommendationData={recommendationData} isLoading={isLoading} /> : <Navigate to="/Login" replace />} />
                <Route path="/Pants" element={isLoggedIn ? <Pants recommendationData={recommendationData} isLoading={isLoading} /> : <Navigate to="/Login" replace />} />
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
