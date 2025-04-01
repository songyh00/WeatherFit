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
import JoinTheMembership from "./JoinTheMembership.jsx";


// 몇월달인지 계산하고 달에 해당하는 계절을 반환하는 함수
const getSeason = () => {
    const month = new Date().getMonth() + 1;
    if (month >= 3 && month <= 5) return "spring";
    if (month >= 6 && month <= 8) return "summer";
    if (month >= 9 && month <= 11) return "autumn";
    return "winter";
};

// 계절별 border색, background색, focus했을때의 색을 넣어논 객체 
const theme = {
    spring: { borderColor: "#FFB6C1", bgColor: "#FFF0F5", focusColor: "#FF69B4" },
    summer: { borderColor: "#4FC3F7", bgColor: "#E0F7FA", focusColor: "#0288D1" },
    autumn: { borderColor: "#FF8A65", bgColor: "#FFF3E0", focusColor: "#D84315" },
    winter: { borderColor: "#90A4AE", bgColor: "#ECEFF1", focusColor: "#455A64" }
};

const Login = () => {
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
        <LoginWrapper
            // action={} 어디로 전달해줘야할지 모르곘어서 일단 두겠습니다 
        >
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
                    <JoinTheMembershipLink to="../JoinTheMembership">아직 회원이 아니신가요? 회원가입</JoinTheMembershipLink>
                </LoginTexts>
            </LoginSection>
        </LoginWrapper>
    );
};

export default Login;