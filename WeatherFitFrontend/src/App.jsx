import React from 'react';
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
import {HeaderSpacer} from "./layout/mainPage.style.js";
import WeatherSection from "./components/WeatherSection.jsx";

function Layout() {
    // 로그인 페이지면 헤더와 푸터를 안보이게 하기 위한 코드
    const location = useLocation();
    const hideHeaderFooter = location.pathname === "/Login" || location.pathname === "/JoinTheMembership" || location.pathname === "/ForgotIdOrPassword";

    return (
        <>
            {!hideHeaderFooter && <Header />}

            <HeaderSpacer />

            {/* 지역검색창 */}
            {(((!hideHeaderFooter && location.pathname !== "/CustomerServiceCenter") && (!hideHeaderFooter && location.pathname !== "/MyPage")) && (!hideHeaderFooter && location.pathname !== "/MyPage")) && <LocationSearch />}
            
            {/* 날씨 띄우는 공간 */}
            {(!hideHeaderFooter && location.pathname !== "/CustomerServiceCenter") && <WeatherSection />}

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
