import React, { useState, useEffect } from "react";
import {
    LoginWrapper,
    LoginSection,
    LoginInput,
    LoginButton,
    LoginTexts,
    JoinTheMembershipLink
} from "../layout/login.style.js";
import { Link } from "react-router-dom";
import logo from '../assets/logo.png';
import { getSeason, theme } from "../components/theme.js"; // ✅ 추가


const Login = () => {
    const [season, setSeason] = useState(getSeason());
    const [inputFocused, setInputFocused] = useState(false);

    const handleFocus = () => setInputFocused(true);
    const handleBlur = () => setInputFocused(false);

    useEffect(() => {
        const interval = setInterval(() => {
            setSeason(getSeason());
        }, 1000 * 60 * 60 * 24);
        return () => clearInterval(interval);
    }, []);

    return (
        <LoginWrapper>
            <Link to="/">
                <img src={logo} alt="WeatherFit Logo" />
            </Link>
            <LoginSection>
                <LoginInput
                    style={{ marginTop: '20px' }}
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
                    placeholder="비밀번호를 입력해주세요"
                />
                <LoginButton
                    borderColor={theme[season].borderColor}
                    bgColor={theme[season].bgColor}
                    focusColor={theme[season].focusColor}
                    textColor={theme[season].textColor}
                    hoverBgColor={theme[season].focusColor}
                >
                    Login
                </LoginButton>
                <LoginTexts>
                    <JoinTheMembershipLink to="../JoinTheMembership">회원가입</JoinTheMembershipLink>
                    <JoinTheMembershipLink to="../ForgotIdOrPassword?page=id">| 아이디찾기 찾기</JoinTheMembershipLink>
                    <JoinTheMembershipLink to="../ForgotIdOrPassword?page=pw">| 비밀번호 찾기</JoinTheMembershipLink>
                </LoginTexts>
            </LoginSection>
        </LoginWrapper>
    );
};

export default Login;