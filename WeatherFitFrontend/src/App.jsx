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
import CustomerServiceCenter from "./pages/CustomerServiceCenter.jsx";
import { HeaderBackground } from "./layout/header.style.js";

function Layout() {
    // 로그인 페이지면 헤더와 푸터를 안보이게 하기 위한 코드
    const location = useLocation();
    const hideHeaderFooter = location.pathname === "/Login" || location.pathname === "/JoinTheMembership";

    return (
        <>
            {!hideHeaderFooter && <Header />}

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

            {/* 푸터 추가 시 여기도 적용 */}
            {/* {!hideHeaderFooter && <Footer />} */}
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
