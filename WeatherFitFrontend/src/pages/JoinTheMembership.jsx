// src/pages/JoinTheMembership.jsx
import React, { useState, useEffect } from "react";
import { LoginWrapper, LoginSection, LoginInput, LoginButton } from "../layout/login.style.js";
import { Link } from "react-router-dom";
import logo from "../assets/logo.png";
import { getSeason, theme } from "../components/theme.js";
import {
    RegisterWrapper
} from "../layout/JoinTheMembership.style.js";

const JoinTheMembership = () => {
    const [season, setSeason] = useState(getSeason());
    const [inputFocused, setInputFocused] = useState(false);

    const handleFocus = () => setInputFocused(true);
    const handleBlur = () => setInputFocused(false);

    useEffect(() => {
        const interval = setInterval(() => {
            setSeason(getSeason());
        }, 1000 * 60 * 60 * 24); // 하루마다 업데이트
        return () => clearInterval(interval);
    }, []);

    return (
        <RegisterWrapper
            // action={}
            // method={}
        >
            <Link to="/">
                <img src={logo} alt="WeatherFit Logo" />
            </Link>

            <LoginSection>
                <LoginInput
                    style={{ marginTop: "20px" }}
                    type="text"
                    borderColor={theme[season].borderColor}
                    bgColor={theme[season].bgColor}
                    focusColor={inputFocused ? theme[season].focusColor : theme[season].borderColor}
                    onFocus={handleFocus}
                    onBlur={handleBlur}
                    placeholder="사용자명을 입력해주세요"
                />
                <LoginInput
                    type="email"
                    borderColor={theme[season].borderColor}
                    bgColor={theme[season].bgColor}
                    focusColor={inputFocused ? theme[season].focusColor : theme[season].borderColor}
                    onFocus={handleFocus}
                    onBlur={handleBlur}
                    placeholder="이메일을 입력해주세요"
                />
                <LoginInput
                    type="password"
                    borderColor={theme[season].borderColor}
                    bgColor={theme[season].bgColor}
                    focusColor={inputFocused ? theme[season].focusColor : theme[season].borderColor}
                    onFocus={handleFocus}
                    onBlur={handleBlur}
                    placeholder="이메일을 입력해주세요"
                />
                <LoginButton
                    borderColor={theme[season].borderColor}
                    bgColor={theme[season].bgColor}
                    focusColor={theme[season].focusColor}
                    hoverBgColor={theme[season].focusColor}
                    style={{marginBottom: '20px'}}
                >
                    회원가입
                </LoginButton>
            </LoginSection>
        </RegisterWrapper>
    );
}

export default JoinTheMembership;
